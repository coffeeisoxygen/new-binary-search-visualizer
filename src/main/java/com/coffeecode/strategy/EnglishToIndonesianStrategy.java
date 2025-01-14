package com.coffeecode.strategy;

import com.coffeecode.model.DictionaryStructure;

public class EnglishToIndonesianStrategy extends AbstractTranslationStrategy {
    public EnglishToIndonesianStrategy(DictionaryStructure dictionaryStructure) {
        super(dictionaryStructure.getEnglishToIndonesian());
    }
}
