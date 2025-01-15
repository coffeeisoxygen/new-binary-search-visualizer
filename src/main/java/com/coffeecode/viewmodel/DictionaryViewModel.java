package com.coffeecode.viewmodel;

import java.io.IOException;
import java.util.List;

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
}
