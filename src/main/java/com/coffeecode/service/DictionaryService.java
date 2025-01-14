package com.coffeecode.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryServiceException;
import com.coffeecode.model.DictionaryData;
import com.coffeecode.model.Language;

public class DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    private final DictionaryData dictionary;
    private final Map<String, String> translationCache;

    public DictionaryService() {
        this.dictionary = new DictionaryData();
        this.translationCache = new ConcurrentHashMap<>();
    }

    public Optional<String> translate(String word) {
        try {
            if (word == null || word.trim().isEmpty()) {
                throw new IllegalArgumentException("Word cannot be null or empty");
            }

            String normalizedWord = word.toLowerCase().trim();

            // Check cache first
            String cachedTranslation = translationCache.get(normalizedWord);
            if (cachedTranslation != null) {
                logger.debug("Cache hit for word: {}", normalizedWord);
                return Optional.of(cachedTranslation);
            }

            if (!dictionary.searchWord(normalizedWord)) {
                logger.debug("Word not found: {}", normalizedWord);
                return Optional.empty();
            }

            String translation = dictionary.translate(normalizedWord);
            translationCache.put(normalizedWord, translation);
            logger.debug("New translation cached: {} -> {}", normalizedWord, translation);

            return Optional.of(translation);
        } catch (Exception e) {
            logger.error("Error translating word: {}", word, e);
            throw new DictionaryServiceException("Failed to translate word: " + word, e);
        }
    }

    public boolean exists(String word) {
        if (word == null || word.trim().isEmpty()) {
            return false;
        }
        return dictionary.searchWord(word.toLowerCase().trim());
    }

    public Language getCurrentLanguage() {
        return dictionary.getCurrentLanguage();
    }

    public Language switchLanguage() {
        translationCache.clear();
        dictionary.switchLanguage();
        return dictionary.getCurrentLanguage();
    }

    public List<String> getAvailableWords() {
        return dictionary.getWordList();
    }

    public void clearCache() {
        translationCache.clear();
        logger.debug("Translation cache cleared");
    }

    public Map<String, String> getCurrentDictionary() {
        return dictionary.getCurrentDictionary();
    }
}