package lab.devops.text_analyzer_rud.mapper;

import lab.devops.text_analyzer_rud.entity.TextStatsEntity;
import lab.devops.text_analyzer_rud.model.TextStatsRes;
import lab.devops.text_analyzer_rud.util.GeneratorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TextStatsMapper {

    private final GeneratorUtils generator;

    public TextStatsEntity toEntity(TextStatsRes model) {
        return TextStatsEntity.builder()
                .originalText(model.getOriginalText())
                .length(model.getLength())
                .wordCount(model.getWordCount())
                .longestWord(model.getLongestWord())
                .averageWordLength(model.getAverageWordLength())
                .mostFrequentWords(model.getMostFrequentWords())
                .createAt(generator.now())
                .updatedAt(generator.now())
                .build();
    }

    public TextStatsRes toModel(TextStatsEntity entity) {
        return TextStatsRes.builder()
                .originalText(entity.getOriginalText())
                .length(entity.getLength())
                .wordCount(entity.getWordCount())
                .longestWord(entity.getLongestWord())
                .averageWordLength(entity.getAverageWordLength())
                .mostFrequentWords(entity.getMostFrequentWords())
                .build();
    }
}
