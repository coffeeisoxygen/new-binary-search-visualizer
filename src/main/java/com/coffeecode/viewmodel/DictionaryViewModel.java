package com.coffeecode.viewmodel;

import java.io.IOException;
import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.SearchResult;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.repository.DictionaryRepository;
import com.coffeecode.services.DictionaryService;

public class DictionaryViewModel {
    private final DictionaryService service;

    public DictionaryViewModel(DictionaryRepository repository) {
        this.service = new DictionaryService(repository);
    }

    public void loadDictionary() throws IOException {
        service.loadDefaultDictionary();
    }

    public List<String> getEnglishWords() {
        return service.getEnglishWords();
    }

    public List<String> getIndonesianWords() {
        return service.getIndonesianWords();
    }

    public List<Vocabulary> getVocabularies() {
        return service.getVocabularies();
    }

    public SearchResult search(String word, Language language) {
        return service.search(word, language);
    }
}
