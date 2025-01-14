package com.coffeecode.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.CustomException;
import com.coffeecode.factory.TranslationStrategyFactory;
import com.coffeecode.strategy.TranslationStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DictionaryData {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryData.class);
    private final TranslationStrategyFactory strategyFactory;
    private TranslationStrategy translationStrategy;
    private DictionaryStructure dictionaryStructure;
    private static final String DICT_PATH = "src/main/resources/dictionary.json";
    private Language currentLanguage;

    public DictionaryData() {
        logger.info("Initializing DictionaryData");
        loadDictionary();
        this.strategyFactory = new TranslationStrategyFactory(dictionaryStructure);
        setLanguage(Language.ENGLISH); // Default to English to Indonesian
    }

    private void loadDictionary() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/dictionary.json");
            
            if (inputStream == null) {
                throw new CustomException("Dictionary file not found in resources");
            }

            dictionaryStructure = mapper.readValue(inputStream, DictionaryStructure.class);
            
            if (dictionaryStructure == null) {
                throw new CustomException("Failed to parse dictionary structure");
            }
            
            logger.info("Dictionary loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading dictionary: ", e);
            throw new CustomException("Failed to load dictionary", e);
        }
    }

    private void setLanguage(Language language) {
        logger.debug("Setting language to: {}", language);
        this.currentLanguage = language;
        this.translationStrategy = strategyFactory.createStrategy(language);
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public List<String> getWordList() {
        logger.trace("Getting word list");
        return translationStrategy.getWordList();
    }

    public String translate(String word) {
        logger.debug("Translating word: {}", word);
        String translation = translationStrategy.getTranslation(word);
        if (!translationStrategy.isValidWord(word)) {
            logger.warn("Translation not found for word: {}", word);
        }
        return translation;
    }

    public void switchLanguage() {
        logger.info("Switching language from: {} to: {}",
                currentLanguage,
                (currentLanguage == Language.ENGLISH) ? Language.INDONESIAN : Language.ENGLISH);
        setLanguage((currentLanguage == Language.ENGLISH) ? Language.INDONESIAN : Language.ENGLISH);
    }

    public boolean searchWord(String word) {
        logger.debug("Searching for word: {}", word);
        List<String> words = translationStrategy.getWordList();
        java.util.Collections.sort(words);

        int left = 0;
        int right = words.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midWord = words.get(mid);

            logger.info("Checking index {}: {}", mid, midWord);

            int comparison = midWord.compareTo(word);
            if (comparison == 0) {
                logger.info("Word found at index: {}", mid);
                return true;
            }

            if (comparison < 0) {
                left = mid + 1;
                logger.debug("Moving right, new left index: {}", left);
            } else {
                right = mid - 1;
                logger.debug("Moving left, new right index: {}", right);
            }
        }

        logger.info("Word not found");
        return false;
    }

    public DictionaryStructure getDictionaryStructure() {
        return dictionaryStructure;
    }
    

    @Override
    public String toString() {
        return "DictionaryData [currentLanguage=" + currentLanguage + "]";
    }

    public Map<String, String> getCurrentDictionary() {
        return translationStrategy.getDictionary();
    }
}
