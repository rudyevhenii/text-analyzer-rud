package lab.devops.text_analyzer_rud.controller;

import jakarta.validation.Valid;
import lab.devops.text_analyzer_rud.model.TextReq;
import lab.devops.text_analyzer_rud.model.TextStatsRes;
import lab.devops.text_analyzer_rud.service.TextStats;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/text-stats")
@RequiredArgsConstructor
public class TextStatsController {

    private final TextStats textStats;

    @PostMapping
    public ResponseEntity<TextStatsRes> createTextStats(@Valid @RequestBody TextReq request) {
        TextStatsRes response = textStats.createTextStats(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
