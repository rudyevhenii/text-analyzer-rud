package lab.devops.text_analyzer_rud.service;

import lab.devops.text_analyzer_rud.model.TextReq;
import lab.devops.text_analyzer_rud.model.TextStatsRes;

public interface TextStatsService {

    TextStatsRes createTextStats(TextReq text);
}
