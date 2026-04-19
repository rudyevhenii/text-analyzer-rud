package lab.devops.text_analyzer_rud.service;

import lab.devops.text_analyzer_rud.model.TextReq;
import lab.devops.text_analyzer_rud.model.TextStats;

import java.util.List;

public interface TextStatsService {

    TextStats createTextStats(TextReq text);

    List<TextStats> findAllTextStats();

    TextStats findTextStatsById(long id);

    TextStats updateTextStats(long id, TextReq text);

    void deleteTextStats(long id);
}
