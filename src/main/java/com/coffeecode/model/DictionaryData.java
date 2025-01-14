package com.coffeecode.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DictionaryData {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryData.class);
    private Map<String, String> currentDictionary;
    private DictionaryStructure dictionaryStructure;
    private static final String DICT_PATH = "src/main/resources/dictionary.json";
    private Language currentLanguage;

    public DictionaryData() {
        logger.info("Initializing DictionaryData");
        loadDictionary();
        setLanguage(Language.ENGLISH); // Default to English to Indonesian
    }

    private void loadDictionary() {
        logger.debug("Loading dictionary from: {}", DICT_PATH);
        try {
            ObjectMapper mapper = new ObjectMapper();
            dictionaryStructure = mapper.readValue(new File(DICT_PATH), DictionaryStructure.class);
            logger.info("Dictionary loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load dictionary", e);
            throw new CustomException("Failed to load dictionary", e);
        }
    }

    private void setLanguage(Language language) {
        logger.debug("Setting language to: {}", language);
        this.currentLanguage = language;
        this.currentDictionary = (language == Language.ENGLISH)
                ? dictionaryStructure.getEnglishToIndonesian()
                : dictionaryStructure.getIndonesianToEnglish();
    }

    public Map<String, String> getCurrentDictionary() {
        return currentDictionary;
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public List<String> getWordList() {
        logger.trace("Getting word list");
        return new ArrayList<>(currentDictionary.keySet());
    }

    public String translate(String word) {
        logger.debug("Translating word: {}", word);
        String translation = currentDictionary.getOrDefault(word, "Translation not found");
        if (!currentDictionary.containsKey(word)) {
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

    @Override
    public String toString() {
        return "DictionaryData [currentDictionary=" + currentDictionary + ", currentLanguage=" + currentLanguage + "]";
    }
}
