package lab.devops.text_analyzer_rud.mapper;

import lab.devops.text_analyzer_rud.entity.TextStatsEntity;
import lab.devops.text_analyzer_rud.model.TextStats;
import lab.devops.text_analyzer_rud.util.GeneratorUtils;
import org.springframework.stereotype.Component;

@Component
public class TextStatsMapper {

    public TextStatsEntity toEntity(TextStats model, GeneratorUtils generatorUtils) {
        return TextStatsEntity.builder()
                .originalText(model.getOriginalText())
                .length(model.getLength())
                .wordCount(model.getWordCount())
                .longestWord(model.getLongestWord())
                .averageWordLength(model.getAverageWordLength())
                .mostFrequentWords(model.getMostFrequentWords())
                .createAt(generatorUtils.now())
                .updatedAt(generatorUtils.now())
                .build();
    }

    public TextStats toModel(TextStatsEntity entity) {
        return TextStats.builder()
                .id(entity.getId())
                .originalText(entity.getOriginalText())
                .length(entity.getLength())
                .wordCount(entity.getWordCount())
                .longestWord(entity.getLongestWord())
                .averageWordLength(entity.getAverageWordLength())
                .mostFrequentWords(entity.getMostFrequentWords())
                .build();
    }

    public TextStatsEntity toEntityUpdate(TextStats model, TextStatsEntity entity, GeneratorUtils generatorUtils) {
        return TextStatsEntity.builder()
                .id(entity.getId())
                .originalText(model.getOriginalText())
                .length(model.getLength())
                .wordCount(model.getWordCount())
                .longestWord(model.getLongestWord())
                .averageWordLength(model.getAverageWordLength())
                .mostFrequentWords(model.getMostFrequentWords())
                .createAt(entity.getCreateAt())
                .updatedAt(generatorUtils.now())
                .build();
    }
}
