package lab.devops.text_analyzer_rud.service.impl;

import lab.devops.text_analyzer_rud.config.TextAnalyzerProperties;
import lab.devops.text_analyzer_rud.entity.TextStatsEntity;
import lab.devops.text_analyzer_rud.mapper.TextStatsMapper;
import lab.devops.text_analyzer_rud.model.TextReq;
import lab.devops.text_analyzer_rud.model.TextStatsRes;
import lab.devops.text_analyzer_rud.repository.TextStatsRepository;
import lab.devops.text_analyzer_rud.util.GeneratorUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import static lab.devops.text_analyzer_rud.service.impl.TextStatsServiceImplTest.TestResources.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TextStatsServiceImplTest {

    @Mock
    private TextStatsRepository repository;

    @Spy
    private TextStatsMapper mapper;

    @Mock
    private GeneratorUtils generatorUtils;

    @Mock
    private TextAnalyzerProperties textAnalyzerProperties;

    @InjectMocks
    private TextStatsServiceImpl service;

    @Test
    void createTextStats_shouldAnalyzeAndSaveSuccessfully() {
        when(repository.save(buildTextStatsEntity())).thenReturn(buildTextStatsSavedEntity());
        when(generatorUtils.now()).thenReturn(CREATED_AT);
        when(textAnalyzerProperties.getWordFrequency()).thenReturn(WORD_FREQUENCY);

        TextStatsRes result = service.createTextStats(buildTextReq());

        assertThat(result).isEqualTo(buildTextStatsRes());

        verify(mapper).toEntity(any(TextStatsRes.class), eq(generatorUtils));
        verify(repository).save(any(TextStatsEntity.class));
        verify(mapper).toModel(any(TextStatsEntity.class));
    }

    static class TestResources {

        public static final String INPUT_TEXT = "Hello World. Hello Java!";

        public static final long ID = 1;
        public static final int LENGTH = 24;
        public static final int WORD_COUNT = 4;
        public static final String LONGEST_WORD = "Hello";
        public static final double AVERAGE_WORD_LENGTH = 4.75;
        public static final Map<String, Long> MOST_FREQUENT_WORDS = getMostFrequentWords();

        public static final Instant CREATED_AT = Instant.parse("2026-04-12T12:00:00Z");
        public static final Instant UPDATED_AT = Instant.parse("2026-04-12T12:00:00Z");

        public static final int WORD_FREQUENCY = 3;

        private static Map<String, Long> getMostFrequentWords() {
            Map<String, Long> mostFrequentWords = new LinkedHashMap<>();
            mostFrequentWords.put("Hello", 2L);
            mostFrequentWords.put("Java", 1L);
            mostFrequentWords.put("World", 1L);

            return mostFrequentWords;
        }

        public static TextReq buildTextReq() {
            return TextReq.builder()
                    .text(INPUT_TEXT)
                    .build();
        }

        public static TextStatsEntity buildTextStatsEntity() {
            return TextStatsEntity.builder()
                    .originalText(INPUT_TEXT)
                    .length(LENGTH)
                    .wordCount(WORD_COUNT)
                    .longestWord(LONGEST_WORD)
                    .averageWordLength(AVERAGE_WORD_LENGTH)
                    .mostFrequentWords(MOST_FREQUENT_WORDS)
                    .createAt(CREATED_AT)
                    .updatedAt(UPDATED_AT)
                    .build();
        }

        public static TextStatsEntity buildTextStatsSavedEntity() {
            return TextStatsEntity.builder()
                    .id(ID)
                    .originalText(INPUT_TEXT)
                    .length(LENGTH)
                    .wordCount(WORD_COUNT)
                    .longestWord(LONGEST_WORD)
                    .averageWordLength(AVERAGE_WORD_LENGTH)
                    .mostFrequentWords(MOST_FREQUENT_WORDS)
                    .createAt(CREATED_AT)
                    .updatedAt(UPDATED_AT)
                    .build();
        }

        public static TextStatsRes buildTextStatsRes() {
            return TextStatsRes.builder()
                    .id(ID)
                    .originalText(INPUT_TEXT)
                    .length(LENGTH)
                    .wordCount(WORD_COUNT)
                    .longestWord(LONGEST_WORD)
                    .averageWordLength(AVERAGE_WORD_LENGTH)
                    .mostFrequentWords(MOST_FREQUENT_WORDS)
                    .build();
        }
    }
}