package lab.devops.text_analyzer_rud.convertor;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Converter
@RequiredArgsConstructor
public class MapToJsonConverter implements AttributeConverter<Map<String, Long>, String> {

    private static final TypeReference<Map<String, Long>> MOST_FREQUENT_WORDS =
            new TypeReference<Map<String, Long>>() {};

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Long> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "{}";
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("Could not convert from Map to String", e);
        }
    }

    @Override
    public Map<String, Long> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(dbData, MOST_FREQUENT_WORDS);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("Could not convert from String to Map", e);
        }
    }
}
