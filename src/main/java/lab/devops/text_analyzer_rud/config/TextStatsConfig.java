package lab.devops.text_analyzer_rud.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TextStatsProperties.class)
public class TextStatsConfig {
}
