package com.coffeecode.viewmodel;

import java.util.List;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ExceptionMessages;
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

    public void loadDictionary() throws DictionaryException {
        try {
            dictionary.loadDefaultDictionary();
            isDictionaryLoaded = true;
        } catch (DictionaryException e) {
            isDictionaryLoaded = false;
            throw e;
        }
    }

    public boolean isDictionaryLoaded() {
        return isDictionaryLoaded;
    }

    public List<String> getEnglishWords() throws DictionaryException {
        validateDictionaryLoaded();
        return dictionary.getEnglishWords();
    }

    public List<String> getIndonesianWords() throws DictionaryException {
        validateDictionaryLoaded();
        return dictionary.getIndonesianWords();
    }

    public List<Vocabulary> getVocabularies() throws DictionaryException {
        validateDictionaryLoaded();
        return dictionary.getVocabularies();
    }

    public SearchResult search(String word, Language language) throws DictionaryException {
        validateDictionaryLoaded();
        if (word == null || word.trim().isEmpty()) {
            throw new DictionaryException(ExceptionMessages.ERR_WORD_EMPTY);
        }
        return dictionary.search(word.trim(), language);
    }

    private void validateDictionaryLoaded() throws DictionaryException {
        if (!isDictionaryLoaded) {
            throw new DictionaryException(ExceptionMessages.ERR_DICT_NOT_LOADED);
        }
    }
}
