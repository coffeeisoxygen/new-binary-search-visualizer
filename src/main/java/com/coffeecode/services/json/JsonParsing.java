package com.coffeecode.services.json;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
import com.coffeecode.model.Vocabulary;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParsing {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonParsing.class);

    public JsonNode parseFile(File file) throws DictionaryException {
        try {
            return objectMapper.readTree(file);
        } catch (IOException e) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    "Failed to read JSON file: " + e.getMessage()
            );
        }
    }

    public List<Vocabulary> convertToVocabularyList(JsonNode vocabularyArray) {
        try {
            return objectMapper.convertValue(
                    vocabularyArray,
                    new TypeReference<List<Vocabulary>>() {
            }
            );
        } catch (IllegalArgumentException e) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    "Error converting JSON to vocabulary: " + e.getMessage()
            );
        }
    }
}
