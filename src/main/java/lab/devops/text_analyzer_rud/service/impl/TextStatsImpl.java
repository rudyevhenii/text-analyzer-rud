package lab.devops.text_analyzer_rud.service.impl;

import lab.devops.text_analyzer_rud.entity.TextStatsEntity;
import lab.devops.text_analyzer_rud.mapper.TextStatsMapper;
import lab.devops.text_analyzer_rud.model.TextReq;
import lab.devops.text_analyzer_rud.model.TextStatsRes;
import lab.devops.text_analyzer_rud.repository.TextStatsRepository;
import lab.devops.text_analyzer_rud.service.TextStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TextStatsImpl implements TextStats {

    private static final String WORD_SPLITTING_REGEX = "\\W+";
    private static final int MOST_FREQUENT_WORDS_COUNT = 3;

    private final TextStatsRepository repository;
    private final TextStatsMapper mapper;

    @Override
    public TextStatsRes createTextStats(TextReq textReq) {
        String text = textReq.getText();
        TextStatsRes textStats = buildTextStats(text);

        TextStatsEntity savedTextStats = repository.save(mapper.toEntity(textStats));

        return mapper.toModel(savedTextStats);
    }

    private TextStatsRes buildTextStats(String text) {
        return TextStatsRes.builder()
                .originalText(text)
                .length(getLength(String::length, text))
                .wordCount(getWordCount(text))
                .longestWord(getLongestWord(text))
                .averageWordLength(getAverageWordLength(text))
                .mostFrequentWord(getMostFrequentWord(text))
                .build();
    }

    private int getLength(Function<String, Integer> textFunc, String text) {
        return textFunc.apply(text);
    }

    private int getWordCount(String text) {
        return getWordList(text).size();
    }

    private int getLongestWord(String text) {
        return getWordList(text).stream()
                .mapToInt(String::length)
                .max()
                .orElseThrow(() -> new IllegalArgumentException("Could not determine the longest word"));
    }

    private double getAverageWordLength(String text) {
        return (double) getWordList(text).stream()
                .mapToInt(String::length)
                .sum() / getWordCount(text);
    }

    private Map<String, Long> getMostFrequentWord(String text) {
        return getWordList(text).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Long::compare).reversed())
                .limit(MOST_FREQUENT_WORDS_COUNT)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> o,
                        LinkedHashMap::new));
    }

    private List<String> getWordList(String text) {
        return Arrays.stream(text.split(WORD_SPLITTING_REGEX)).toList();
    }

}
