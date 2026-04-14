package lab.devops.text_analyzer_rud.service.impl;

import lab.devops.text_analyzer_rud.config.TextStatsProperties;
import lab.devops.text_analyzer_rud.entity.TextStatsEntity;
import lab.devops.text_analyzer_rud.mapper.TextStatsMapper;
import lab.devops.text_analyzer_rud.model.TextReq;
import lab.devops.text_analyzer_rud.model.TextStats;
import lab.devops.text_analyzer_rud.repository.TextStatsRepository;
import lab.devops.text_analyzer_rud.service.TextStatsService;
import lab.devops.text_analyzer_rud.util.GeneratorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextStatsServiceImpl implements TextStatsService {

    private static final String WORD_SPLITTING_REGEX = "\\W+";

    private final TextStatsRepository repository;
    private final TextStatsMapper mapper;
    private final GeneratorUtils generatorUtils;
    private final TextStatsProperties textStatsProperties;

    @Override
    public TextStats createTextStats(TextReq textReq) {
        log.info("Starting analyzing text.");
        TextStats textStats = buildTextStats(textReq.getText());

        log.info("Saving analyzed text to db.");
        TextStatsEntity savedTextStats = repository.save(mapper.toEntity(textStats, generatorUtils));

        return mapper.toModel(savedTextStats);
    }

    @Override
    public List<TextStats> findAllTextStats() {
        log.info("Fetching all analyzed texts.");

        return repository.findAll().stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public TextStats findTextStatsById(long id) {
        log.info("Searching for text stats with id {}", id);

        TextStatsEntity textStatsEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Text statistics with id %d does not exist.".formatted(id)));

        return mapper.toModel(textStatsEntity);
    }

    private TextStats buildTextStats(String text) {
        return TextStats.builder()
                .originalText(text)
                .length(getLength(String::length, text))
                .wordCount(getWordCount(text))
                .longestWord(getLongestWord(text))
                .averageWordLength(getAverageWordLength(text))
                .mostFrequentWords(getMostFrequentWords(text))
                .build();
    }

    private int getLength(Function<String, Integer> textFunc, String text) {
        return textFunc.apply(text);
    }

    private int getWordCount(String text) {
        return getWordList(text).size();
    }

    private String getLongestWord(String text) {
        return getWordList(text).stream()
                .min(Comparator.comparing(String::length).reversed()
                        .thenComparing(Comparator.naturalOrder()))
                .orElseThrow(() -> new IllegalArgumentException("Could not determine the longest word"));
    }

    private double getAverageWordLength(String text) {
        return (double) getWordList(text).stream()
                .mapToInt(String::length)
                .sum() / getWordCount(text);
    }

    private Map<String, Long> getMostFrequentWords(String text) {
        return getWordList(text).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(textStatsProperties.getWordFrequency())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> o,
                        LinkedHashMap::new));
    }

    private List<String> getWordList(String text) {
        return Arrays.stream(text.split(WORD_SPLITTING_REGEX)).toList();
    }

}
