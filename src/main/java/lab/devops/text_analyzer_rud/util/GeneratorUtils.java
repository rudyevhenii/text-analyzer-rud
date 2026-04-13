package lab.devops.text_analyzer_rud.util;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class GeneratorUtils {

    public Instant now() {
        return Instant.now();
    }
}
