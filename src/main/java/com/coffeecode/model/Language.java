package com.coffeecode.model;

import java.util.function.Function;

public enum Language {
    ENGLISH(Vocabulary::english),
    INDONESIAN(Vocabulary::indonesian);

    private final Function<Vocabulary, String> wordExtractor;

    Language(Function<Vocabulary, String> wordExtractor) {
        this.wordExtractor = wordExtractor;
    }

    public String getWord(Vocabulary vocabulary) {
        return wordExtractor.apply(vocabulary);
    }

    public Language getOpposite() {
        return this == ENGLISH ? INDONESIAN : ENGLISH;
    }
}