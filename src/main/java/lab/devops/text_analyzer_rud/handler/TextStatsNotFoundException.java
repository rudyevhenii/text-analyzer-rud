package lab.devops.text_analyzer_rud.handler;

public class TextStatsNotFoundException extends RuntimeException {
    public TextStatsNotFoundException(String message) {
        super(message);
    }
}
