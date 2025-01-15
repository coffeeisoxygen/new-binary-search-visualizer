package com.coffeecode.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coffeecode.model.Vocabulary;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonDictionaryRepository implements DictionaryRepository {
    private final ObjectMapper objectMapper;

    public JsonDictionaryRepository() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Vocabulary> loadVocabularies(String filePath) throws IOException {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path cannot be empty");
        }
        JsonNode root = objectMapper.readTree(new File(filePath));
        JsonNode vocabularyArray = root.get("vocabulary");
        return objectMapper.convertValue(vocabularyArray,
                new TypeReference<List<Vocabulary>>() {
                });
    }
}
