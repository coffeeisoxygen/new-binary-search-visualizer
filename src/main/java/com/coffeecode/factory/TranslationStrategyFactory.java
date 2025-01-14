package com.coffeecode.factory;

import com.coffeecode.model.DictionaryStructure;
import com.coffeecode.model.Language;
import com.coffeecode.strategy.EnglishToIndonesianStrategy;
import com.coffeecode.strategy.IndonesianToEnglishStrategy;
import com.coffeecode.strategy.TranslationStrategy;

public class TranslationStrategyFactory {
    private final DictionaryStructure dictionaryStructure;

    public TranslationStrategyFactory(DictionaryStructure dictionaryStructure) {
        this.dictionaryStructure = dictionaryStructure;
    }

    public TranslationStrategy createStrategy(Language language) {
        return switch (language) {
            case ENGLISH -> new EnglishToIndonesianStrategy(dictionaryStructure);
            case INDONESIAN -> new IndonesianToEnglishStrategy(dictionaryStructure);
        };
    }
}
