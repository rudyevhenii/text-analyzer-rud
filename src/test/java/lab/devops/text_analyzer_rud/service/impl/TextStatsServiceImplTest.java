package lab.devops.text_analyzer_rud.service.impl;

import lab.devops.text_analyzer_rud.config.TextStatsProperties;
import lab.devops.text_analyzer_rud.entity.TextStatsEntity;
import lab.devops.text_analyzer_rud.mapper.TextStatsMapper;
import lab.devops.text_analyzer_rud.model.TextReq;
import lab.devops.text_analyzer_rud.model.TextStats;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static lab.devops.text_analyzer_rud.service.impl.TextStatsServiceImplTest.TestResources.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TextStatsServiceImplTest {

    @Mock
    private TextStatsRepository repository;

    @Spy
    private TextStatsMapper mapper;

    @Mock
    private GeneratorUtils generatorUtils;

    @Mock
    private TextStatsProperties textStatsProperties;

    @InjectMocks
    private TextStatsServiceImpl service;

    @Test
    void createTextStats_shouldAnalyzeAndSaveSuccessfully() {
        when(repository.save(buildTextStatsEntity())).thenReturn(buildTextStatsSavedEntity1());
        when(generatorUtils.now()).thenReturn(CREATED_AT);
        when(textStatsProperties.getWordFrequency()).thenReturn(WORD_FREQUENCY);

        TextStats result = service.createTextStats(buildTextReq());

        assertThat(result).isEqualTo(buildTextStats1());

        verify(mapper).toEntity(any(TextStats.class), eq(generatorUtils));
        verify(repository).save(any(TextStatsEntity.class));
        verify(mapper).toModel(any(TextStatsEntity.class));
    }

    @Test
    void findAllTextStats_shouldSuccessfullyReturnAllTextStats() {
        when(repository.findAll()).thenReturn(buildTextStatsEntityList());

        List<TextStats> result = service.findAllTextStats();

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(buildTextStatsEntityList());

        verify(repository).findAll();
        verify(mapper, times(buildTextStatsEntityList().size())).toModel(any(TextStatsEntity.class));
    }

    @Test
    void findTextStatsById_shouldSuccessfullyReturnTextStatsByGivenId() {
        when(repository.findById(ID)).thenReturn(Optional.of(buildTextStatsSavedEntity1()));

        TextStats result = service.findTextStatsById(ID);

        assertThat(result).isEqualTo(buildTextStats1());

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

        public static final String INPUT_TEXT_2 = """
                Spring Boot is awesome!
                (Spring makes Java better, and Java makes Spring possible).
                """;

        public static final long ID_2 = 2;
        public static final int LENGTH_2 = 83;
        public static final int WORD_COUNT_2 = 13;
        public static final String LONGEST_WORD_2 = "possible";
        public static final double AVERAGE_WORD_LENGTH_2 = 5.076923076923077;
        public static final Map<String, Long> MOST_FREQUENT_WORDS_2 = getMostFrequentWords2();

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

        private static Map<String, Long> getMostFrequentWords2() {
            Map<String, Long> mostFrequentWords = new LinkedHashMap<>();
            mostFrequentWords.put("Spring", 3L);
            mostFrequentWords.put("Java", 2L);
            mostFrequentWords.put("makes", 2L);

            return mostFrequentWords;
        }

        public static TextReq buildTextReq() {
            return TextReq.builder()
                    .text(INPUT_TEXT)
                    .build();
        }

        public static List<TextStatsEntity> buildTextStatsEntityList() {
            return List.of(
                    buildTextStatsSavedEntity1(),
                    buildTextStatsSavedEntity2()
            );
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

        public static TextStatsEntity buildTextStatsSavedEntity1() {
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

        public static TextStatsEntity buildTextStatsSavedEntity2() {
            return TextStatsEntity.builder()
                    .id(ID_2)
                    .originalText(INPUT_TEXT_2)
                    .length(LENGTH_2)
                    .wordCount(WORD_COUNT_2)
                    .longestWord(LONGEST_WORD_2)
                    .averageWordLength(AVERAGE_WORD_LENGTH_2)
                    .mostFrequentWords(MOST_FREQUENT_WORDS_2)
                    .createAt(CREATED_AT)
                    .updatedAt(UPDATED_AT)
                    .build();
        }

        public static List<TextStats> buildTextStatsList() {
            return List.of(
                    buildTextStats1(),
                    buildTextStats2()
            );
        }

        public static TextStats buildTextStats1() {
            return TextStats.builder()
                    .id(ID)
                    .originalText(INPUT_TEXT)
                    .length(LENGTH)
                    .wordCount(WORD_COUNT)
                    .longestWord(LONGEST_WORD)
                    .averageWordLength(AVERAGE_WORD_LENGTH)
                    .mostFrequentWords(MOST_FREQUENT_WORDS)
                    .build();
        }

        public static TextStats buildTextStats2() {
            return TextStats.builder()
                    .id(ID_2)
                    .originalText(INPUT_TEXT_2)
                    .length(LENGTH_2)
                    .wordCount(WORD_COUNT_2)
                    .longestWord(LONGEST_WORD_2)
                    .averageWordLength(AVERAGE_WORD_LENGTH_2)
                    .mostFrequentWords(MOST_FREQUENT_WORDS_2)
                    .build();
        }
    }
}