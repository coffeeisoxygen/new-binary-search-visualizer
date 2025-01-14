package com.coffeecode.strategy;

import com.coffeecode.model.DictionaryStructure;

public class IndonesianToEnglishStrategy extends AbstractTranslationStrategy {
    public IndonesianToEnglishStrategy(DictionaryStructure dictionaryStructure) {
        super(dictionaryStructure.getIndonesianToEnglish());
    }
}
