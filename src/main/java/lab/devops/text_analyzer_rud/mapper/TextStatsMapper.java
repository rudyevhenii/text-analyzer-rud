package lab.devops.text_analyzer_rud.mapper;

import lab.devops.text_analyzer_rud.entity.TextStatsEntity;
import lab.devops.text_analyzer_rud.model.TextStatsRes;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TextStatsMapper {

    public TextStatsEntity toEntity(TextStatsRes model) {
        return TextStatsEntity.builder()
                .originalText(model.getOriginalText())
                .length(model.getLength())
                .wordCount(model.getWordCount())
                .longestWord(model.getLongestWord())
                .averageWordLength(model.getAverageWordLength())
                .mostFrequentWord(model.getMostFrequentWord())
                .createAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    public TextStatsRes toModel(TextStatsEntity entity) {
        return TextStatsRes.builder()
                .originalText(entity.getOriginalText())
                .length(entity.getLength())
                .wordCount(entity.getWordCount())
                .longestWord(entity.getLongestWord())
                .averageWordLength(entity.getAverageWordLength())
                .mostFrequentWord(entity.getMostFrequentWord())
                .build();
    }
}
