package com.coffeecode.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.coffeecode.model.Vocabulary;
import com.coffeecode.repository.DictionaryRepository;

public class DictionaryService {
    private final DictionaryRepository repository;
    private List<Vocabulary> vocabularies;

    public DictionaryService(DictionaryRepository repository) {
        this.repository = repository;
    }

    public void loadDictionary(String filePath) throws IOException {
        this.vocabularies = repository.loadVocabularies(filePath);
    }

    public List<String> getEnglishWords() {
        return vocabularies.stream()
                .map(Vocabulary::english)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getIndonesianWords() {
        return vocabularies.stream()
                .map(Vocabulary::indonesian)
                .sorted()
                .collect(Collectors.toList());
    }
}
