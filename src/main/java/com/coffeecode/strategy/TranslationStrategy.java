package com.coffeecode.strategy;

import java.util.List;
import java.util.Map;

public interface TranslationStrategy {
    Map<String, String> getDictionary();
    String getTranslation(String word);
    boolean isValidWord(String word);
    List<String> getWordList();
}
