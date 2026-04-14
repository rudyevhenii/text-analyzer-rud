package lab.devops.text_analyzer_rud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextStats {
    private long id;
    private String originalText;
    private int length;
    private int wordCount;
    private String longestWord;
    private double averageWordLength;
    private Map<String, Long> mostFrequentWords;
}
