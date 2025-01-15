package com.coffeecode.viewmodel;

import java.io.IOException;
import java.util.List;

import com.coffeecode.model.DictionaryModel;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.repository.DictionaryRepository;

public class DictionaryViewModel {
    private final DictionaryModel model;
    private final DictionaryRepository repository;

    public DictionaryViewModel(DictionaryRepository repository) {
        this.repository = repository;
        this.model = new DictionaryModel();
    }

    public void loadDictionary(String filePath) throws IOException {
        List<Vocabulary> vocabularies = repository.loadVocabularies(filePath);
        model.setVocabularies(vocabularies);
    }

    public List<String> getEnglishWords() {
        return model.getVocabularies().stream()
                .map(Vocabulary::getEnglish)
                .sorted()
                .toList(); // Returns an unmodifiable List
    }

    public List<String> getIndonesianWords() {
        return model.getVocabularies().stream()
                .map(Vocabulary::getIndonesian)
                .sorted()
                .toList(); // Returns an unmodifiable List
    }
}
