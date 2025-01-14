package com.coffeecode.model;

import java.util.Map;

public class DictionaryStructure {
    private Map<String, String> englishToIndonesian;
    private Map<String, String> indonesianToEnglish;

    public Map<String, String> getEnglishToIndonesian() {
        return englishToIndonesian;
    }

    public void setEnglishToIndonesian(Map<String, String> englishToIndonesian) {
        this.englishToIndonesian = englishToIndonesian;
    }

    public Map<String, String> getIndonesianToEnglish() {
        return indonesianToEnglish;
    }

    public void setIndonesianToEnglish(Map<String, String> indonesianToEnglish) {
        this.indonesianToEnglish = indonesianToEnglish;
    }
}
