package com.coffeecode.viewmodel;

import java.io.IOException;
import java.util.List;

import com.coffeecode.model.IDictionary;
import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.search.SearchResult;

public class DictionaryViewModel {

    private final IDictionary dictionary;
    private boolean isDictionaryLoaded;

    public DictionaryViewModel(IDictionary dictionary) {
        this.dictionary = dictionary;
        this.isDictionaryLoaded = false;
    }

    public void loadDictionary() throws IOException {
        try {
            dictionary.loadDefaultDictionary();
            isDictionaryLoaded = true;
        } catch (IOException e) {
            isDictionaryLoaded = false;
            throw new IOException("Failed to load dictionary", e);
        }
    }

    public boolean isDictionaryLoaded() {
        return isDictionaryLoaded;
    }

    public List<String> getEnglishWords() {
        validateDictionaryLoaded();
        return dictionary.getEnglishWords();
    }

    public List<String> getIndonesianWords() {
        validateDictionaryLoaded();
        return dictionary.getIndonesianWords();
    }

    public List<Vocabulary> getVocabularies() {
        validateDictionaryLoaded();
        return dictionary.getVocabularies();
    }

    public SearchResult search(String word, Language language) {
        validateDictionaryLoaded();
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("Search word cannot be empty");
        }
        return dictionary.search(word.trim(), language);
    }

    private void validateDictionaryLoaded() {
        if (!isDictionaryLoaded) {
            throw new IllegalStateException("Dictionary not loaded. Call loadDictionary() first");
        }
    }
}
