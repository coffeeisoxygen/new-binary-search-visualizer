package com.coffeecode.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AbstractTranslationStrategy implements TranslationStrategy {
    protected final Map<String, String> dictionary;

    protected AbstractTranslationStrategy(Map<String, String> dictionary) {
        validateDictionary(dictionary);
        this.dictionary = dictionary;
    }

    private void validateDictionary(Map<String, String> dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("Dictionary cannot be null");
        }
        if (dictionary.isEmpty()) {
            throw new IllegalArgumentException("Dictionary cannot be empty");
        }
    }

    @Override
    public Map<String, String> getDictionary() {
        return Collections.unmodifiableMap(dictionary);
    }

    @Override
    public String getTranslation(String word) {
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("Word cannot be null or empty");
        }
        return dictionary.getOrDefault(word.toLowerCase(), "Translation not found");
    }

    @Override
    public boolean isValidWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            return false;
        }
        return dictionary.containsKey(word.toLowerCase());
    }

    @Override
    public List<String> getWordList() {
        return Collections.unmodifiableList(new ArrayList<>(dictionary.keySet()));
    }
}
