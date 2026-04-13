package lab.devops.text_analyzer_rud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "text-analyzer")
public class TextAnalyzerProperties {
    private int wordFrequency;
}
