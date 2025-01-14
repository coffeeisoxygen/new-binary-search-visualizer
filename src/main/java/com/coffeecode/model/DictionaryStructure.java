package com.coffeecode.model;

import java.util.Map;

import com.coffeecode.exception.DictionaryValidationException;

public class DictionaryStructure {
    private Map<String, String> englishToIndonesian;
    private Map<String, String> indonesianToEnglish;

    public DictionaryStructure(Map<String, String> englishToIndonesian,
            Map<String, String> indonesianToEnglish) {
        validateDictionaries(englishToIndonesian, indonesianToEnglish);
        this.englishToIndonesian = englishToIndonesian;
        this.indonesianToEnglish = indonesianToEnglish;
    }

    private void validateDictionaries(Map<String, String> engToInd,
            Map<String, String> indToEng) {
        if (engToInd == null || indToEng == null) {
            throw new DictionaryValidationException("Dictionaries cannot be null");
        }
        if (engToInd.isEmpty() || indToEng.isEmpty()) {
            throw new DictionaryValidationException("Dictionaries cannot be empty");
        }
        validateDictionaryEntries(engToInd, "English-Indonesian");
        validateDictionaryEntries(indToEng, "Indonesian-English");
    }

    private void validateDictionaryEntries(Map<String, String> dictionary,
            String dictionaryName) {
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            if (entry.getKey() == null || entry.getKey().trim().isEmpty()) {
                throw new DictionaryValidationException(
                        dictionaryName + " dictionary contains null or empty key");
            }
            if (entry.getValue() == null || entry.getValue().trim().isEmpty()) {
                throw new DictionaryValidationException(
                        dictionaryName + " dictionary contains null or empty value for key: "
                                + entry.getKey());
            }
        }
    }

    public Map<String, String> getEnglishToIndonesian() {
        return englishToIndonesian;
    }

    public Map<String, String> getIndonesianToEnglish() {
        return indonesianToEnglish;
    }

    protected void setEnglishToIndonesian(Map<String, String> englishToIndonesian) {
        validateDictionaryEntries(englishToIndonesian, "English-Indonesian");
        this.englishToIndonesian = englishToIndonesian;
    }

    protected void setIndonesianToEnglish(Map<String, String> indonesianToEnglish) {
        validateDictionaryEntries(indonesianToEnglish, "Indonesian-English");
        this.indonesianToEnglish = indonesianToEnglish;
    }
}
