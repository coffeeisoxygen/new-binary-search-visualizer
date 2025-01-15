package com.coffeecode.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService {

    private static final String DEFAULT_DICTIONARY_PATH = "src/main/resources/vocabulary.json";
    private final ObjectMapper objectMapper;

    public FileService() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Vocabulary> loadDefaultDictionary() throws IOException {
        return loadVocabularies(DEFAULT_DICTIONARY_PATH);
    }

    public List<Vocabulary> loadVocabularies(String filePath) throws IOException {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path cannot be empty");
        }
        var root = objectMapper.readTree(new File(filePath));
        var vocabularyArray = root.get("vocabulary");
        return objectMapper.convertValue(vocabularyArray,
                new TypeReference<List<Vocabulary>>() {
        });
    }
}
