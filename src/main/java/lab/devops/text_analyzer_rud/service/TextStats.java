package lab.devops.text_analyzer_rud.service;

import lab.devops.text_analyzer_rud.model.TextReq;
import lab.devops.text_analyzer_rud.model.TextStatsRes;

public interface TextStats {

    TextStatsRes createTextStats(TextReq text);
}
