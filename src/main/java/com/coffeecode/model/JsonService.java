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

public class JsonService implements IJsonService {

    private static final Logger logger = LoggerFactory.getLogger(JsonService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final int MAX_VOCABULARY_SIZE = 1000;
    private static final int MAX_WORD_LENGTH = 50;

    @Override
    public List<Vocabulary> parseVocabularyFile(File file) throws DictionaryException {
        try {
            JsonNode root = objectMapper.readTree(file);
            validateJsonStructure(root);
            JsonNode vocabularyArray = root.get("vocabulary");
            return convertToVocabularyList(vocabularyArray, file.getPath());
        } catch (IOException e) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    "Failed to read JSON file: " + e.getMessage()
            );
        }
    }

    private void validateJsonStructure(JsonNode root) {
        if (root == null || !root.has("vocabulary")) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    "Invalid JSON: missing vocabulary array"
            );
        }

        JsonNode vocabularyArray = root.get("vocabulary");
        validateVocabularyArray(vocabularyArray);
        validateArraySize(vocabularyArray);
        validateArrayEntries(vocabularyArray);
    }

    private void validateVocabularyArray(JsonNode vocabularyArray) {
        if (!vocabularyArray.isArray()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    "Invalid JSON: vocabulary must be an array"
            );
        }

        if (vocabularyArray.size() == 0) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    "Invalid JSON: vocabulary array cannot be empty"
            );
        }
    }

    private void validateArraySize(JsonNode vocabularyArray) {
        if (vocabularyArray.size() > MAX_VOCABULARY_SIZE) {
            throw new DictionaryException(
                    ErrorCode.FILE_TOO_LARGE,
                    String.format("Vocabulary size exceeds limit of %d entries", MAX_VOCABULARY_SIZE)
            );
        }
    }

    private void validateArrayEntries(JsonNode vocabularyArray) {
        for (JsonNode entry : vocabularyArray) {
            if (!entry.has("english") || !entry.has("indonesian")) {
                throw new DictionaryException(
                        ErrorCode.INVALID_JSON,
                        "Invalid JSON: each entry must have english and indonesian fields"
                );
            }

            String english = entry.get("english").asText();
            String indonesian = entry.get("indonesian").asText();

            if (!isValidWord(english) || !isValidWord(indonesian)) {
                throw new DictionaryException(
                        ErrorCode.INVALID_JSON,
                        "Invalid JSON: words must contain only letters, spaces, and hyphens"
                );
            }

            validateWordLength(english, "English");
            validateWordLength(indonesian, "Indonesian");
        }
    }

    private boolean isValidWord(String word) {
        return word != null
                && !word.isBlank()
                && word.length() <= MAX_WORD_LENGTH
                && word.matches("^[a-zA-Z\\s-]+$");
    }

    private void validateWordLength(String word, String language) {
        if (word.length() > MAX_WORD_LENGTH) {
            throw new DictionaryException(
                    ErrorCode.INVALID_WORD,
                    String.format("%s word exceeds maximum length of %d characters: %s",
                            language, MAX_WORD_LENGTH, word)
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
        checkDuplicates(vocabularies);
    }

    private void checkDuplicates(List<Vocabulary> vocabularies) {
        Set<String> englishWords = new HashSet<>();
        Set<String> indonesianWords = new HashSet<>();

        for (Vocabulary vocab : vocabularies) {
            if (!englishWords.add(vocab.english().toLowerCase())) {
                throw new DictionaryException(
                        ErrorCode.DUPLICATE_ENTRY,
                        String.format("Duplicate English word found: %s", vocab.english())
                );
            }
            if (!indonesianWords.add(vocab.indonesian().toLowerCase())) {
                throw new DictionaryException(
                        ErrorCode.DUPLICATE_ENTRY,
                        String.format("Duplicate Indonesian word found: %s", vocab.indonesian())
                );
            }
        }
    }
}
