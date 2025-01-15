package com.coffeecode.model;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonService {

    private static final Logger logger = LoggerFactory.getLogger(JsonService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<Vocabulary> parseVocabularyFile(File file) throws DictionaryException {
        JsonNode vocabularyArray = parseAndValidateJson(file);
        return convertToVocabularyList(vocabularyArray, file.getPath());
    }

    private JsonNode parseAndValidateJson(File file) throws DictionaryException {
        try {
            JsonNode root = objectMapper.readTree(file);
            JsonNode vocabularyArray = root.get("vocabulary");

            if (vocabularyArray == null || !vocabularyArray.isArray()) {
                throw new DictionaryException(
                        ErrorCode.INVALID_JSON,
                        "Invalid JSON structure: missing vocabulary array"
                );
            }

            return vocabularyArray;
        } catch (IOException e) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    "Failed to read JSON file: " + e.getMessage()
            );
        }
    }

    private List<Vocabulary> convertToVocabularyList(JsonNode vocabularyArray, String filePath) {
        try {
            List<Vocabulary> vocabularies = objectMapper.convertValue(
                    vocabularyArray,
                    new TypeReference<List<Vocabulary>>() {
            }
            );

            validateVocabularies(vocabularies);
            logger.info("Successfully parsed {} vocabularies from {}", vocabularies.size(), filePath);

            return vocabularies;
        } catch (IllegalArgumentException e) {
            throw new DictionaryException(null, "Error parsing JSON file: " + e.getMessage());
        }
    }

    private void validateVocabularies(List<Vocabulary> vocabularies) {
        Set<String> englishWords = new HashSet<>();
        Set<String> indonesianWords = new HashSet<>();

        for (Vocabulary vocab : vocabularies) {
            validateVocabulary(vocab);
            checkDuplicate(vocab, englishWords, indonesianWords);
        }
    }

    private void validateVocabulary(Vocabulary vocab) {
        if (vocab.english() == null || vocab.english().isBlank()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_WORD,
                    "English word cannot be empty"
            );
        }
        if (vocab.indonesian() == null || vocab.indonesian().isBlank()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_WORD,
                    "Indonesian word cannot be empty"
            );
        }
    }

    private void checkDuplicate(Vocabulary vocab, Set<String> englishWords, Set<String> indonesianWords) {
        if (!englishWords.add(vocab.english().toLowerCase())) {
            throw new DictionaryException(
                    ErrorCode.DUPLICATE_ENTRY,
                    "Duplicate English word found: " + vocab.english()
            );
        }
        if (!indonesianWords.add(vocab.indonesian().toLowerCase())) {
            throw new DictionaryException(
                    ErrorCode.DUPLICATE_ENTRY,
                    "Duplicate Indonesian word found: " + vocab.indonesian()
            );
        }
    }
}
