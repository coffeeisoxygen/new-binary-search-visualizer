package com.coffeecode.viewmodel;

import java.util.List;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
import com.coffeecode.exception.ErrorHandler;
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

        public void loadDictionary() {
        try {
            dictionary.loadDefaultDictionary();
            isDictionaryLoaded = true;
        } catch (DictionaryException e) {
            isDictionaryLoaded = false;
            ErrorHandler.handle(e);
        } catch (Exception e) {
            isDictionaryLoaded = false;
            ErrorHandler.handle(e);
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

    public SearchResult search(String word, Language language) {
        validateDictionaryLoaded();
        if (word == null || word.trim().isEmpty()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_WORD,
                    "Search word cannot be empty"
            );
        }
        return dictionary.search(word.trim(), language);
    }

    private void validateDictionaryLoaded() {
        if (!isDictionaryLoaded) {
            throw new DictionaryException(
                    ErrorCode.DICTIONARY_NOT_LOADED,
                    "Dictionary not loaded. Please load a dictionary first."
            );
        }
    }
}
