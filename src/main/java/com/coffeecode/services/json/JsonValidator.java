package com.coffeecode.services.json;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
import com.coffeecode.model.Vocabulary;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonValidator {

    // Constants
    private static final int MAX_VOCABULARY_SIZE = 1000;
    private static final int MAX_WORD_LENGTH = 50;
    private static final String WORD_PATTERN = "^[a-zA-Z\\s-]+$";
    private static final String ENGLISH_KEY = "english";
    private static final String INDONESIAN_KEY = "indonesian";

    // Main validation methods
    public void validateJsonStructure(JsonNode root) {
        validateRoot(root);
        JsonNode vocabularyArray = root.get("vocabulary");
        validateVocabularyArray(vocabularyArray);
        validateArraySize(vocabularyArray);
        validateArrayEntries(vocabularyArray);
    }

    public void validateVocabularies(List<Vocabulary> vocabularies) {
        validateDuplicates(vocabularies);
    }

    // Root structure validation
    private void validateRoot(JsonNode root) {
        if (root == null || !root.has("vocabulary")) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    "Invalid JSON: missing vocabulary array"
            );
        }
    }

    // Array validation methods
    private void validateVocabularyArray(JsonNode array) {
        if (!array.isArray() || array.size() == 0) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    "Invalid JSON: vocabulary must be a non-empty array"
            );
        }
    }

    private void validateArraySize(JsonNode vocabularyArray) {
        if (vocabularyArray.size() > MAX_VOCABULARY_SIZE) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    "Invalid JSON: vocabulary array exceeds maximum size"
            );
        }
    }

    private void validateArrayEntries(JsonNode vocabularyArray) {
        for (JsonNode entry : vocabularyArray) {
            if (!entry.has(ENGLISH_KEY) || !entry.has(INDONESIAN_KEY)) {
                throw new DictionaryException(
                        ErrorCode.INVALID_JSON,
                        "Invalid JSON: missing english or indonesian in vocabulary entry"
                );
            }
            String english = entry.get(ENGLISH_KEY).asText();
            String indonesian = entry.get(INDONESIAN_KEY).asText();

            validateWord(english, ENGLISH_KEY);
            validateWord(indonesian, INDONESIAN_KEY);
        }
    }

    private void validateWord(String word, String language) {
        if (word.length() > MAX_WORD_LENGTH) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    String.format("Invalid %s word length: %s", language, word)
            );
        }
        if (!word.matches(WORD_PATTERN)) {
            throw new DictionaryException(
                    ErrorCode.INVALID_JSON,
                    String.format("Invalid %s word format: %s", language, word)
            );
        }
    }

    private void validateDuplicates(List<Vocabulary> vocabularies) {
        Set<String> englishWords = new HashSet<>();
        Set<String> indonesianWords = new HashSet<>();

        for (Vocabulary vocab : vocabularies) {
            if (!englishWords.add(vocab.english().toLowerCase())) {
                throw new DictionaryException(
                        ErrorCode.DUPLICATE_ENTRY,
                        "Duplicate English word: " + vocab.english()
                );
            }
            if (!indonesianWords.add(vocab.indonesian().toLowerCase())) {
                throw new DictionaryException(
                        ErrorCode.DUPLICATE_ENTRY,
                        "Duplicate Indonesian word: " + vocab.indonesian()
                );
            }
        }
    }
}
