package com.coffeecode.services;

import java.io.IOException;
import java.util.List;

import com.coffeecode.model.Vocabulary;
import com.coffeecode.repository.DictionaryRepository;

public class DictionaryService {
    private final DictionaryRepository repository;
    private List<Vocabulary> vocabularies;
    private static final String DEFAULT_DICTIONARY_PATH = "src/main/resources/vocabulary.json";

    public DictionaryService(DictionaryRepository repository) {
        this.repository = repository;
    }

    public void loadDefaultDictionary() throws IOException {
        loadDictionary(DEFAULT_DICTIONARY_PATH);
    }

    public void loadDictionary(String filePath) throws IOException {
        this.vocabularies = repository.loadVocabularies(filePath);
    }

    public List<String> getEnglishWords() {
        return vocabularies.stream()
                .map(Vocabulary::english)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }

    public List<String> getIndonesianWords() {
        return vocabularies.stream()
                .map(Vocabulary::indonesian)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }

    public List<Vocabulary> getVocabularies() {
        return vocabularies;
    }
}
