package lab.devops.text_analyzer_rud.controller;

import jakarta.validation.Valid;
import lab.devops.text_analyzer_rud.model.TextReq;
import lab.devops.text_analyzer_rud.model.TextStats;
import lab.devops.text_analyzer_rud.service.TextStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/text-stats")
@RequiredArgsConstructor
public class TextStatsController {

    private final TextStatsService textStatsService;

    @PostMapping
    public ResponseEntity<TextStats> createTextStats(@Valid @RequestBody TextReq request) {
        TextStats response = textStatsService.createTextStats(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TextStats>> finAllTextStats() {
        List<TextStats> response = textStatsService.findAllTextStats();

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TextStats> findTextStatsById(@PathVariable long id) {
        TextStats response = textStatsService.findTextStatsById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TextStats> updateTextStats(@PathVariable long id, @Valid @RequestBody TextReq textReq) {
        TextStats response = textStatsService.updateTextStats(id, textReq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTextStats(@PathVariable long id) {
        textStatsService.deleteTextStats(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
