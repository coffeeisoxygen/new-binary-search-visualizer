package com.coffeecode.model;

import java.util.ArrayList;
import java.util.List;

public class DictionaryModel {
    private List<Vocabulary> vocabularies;

    public DictionaryModel() {
        this.vocabularies = new ArrayList<>();
    }

    public void setVocabularies(List<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }

    public List<Vocabulary> getVocabularies() {
        return new ArrayList<>(vocabularies);
    }
}
