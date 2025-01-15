package com.coffeecode.model;

import java.util.List;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.search.SearchResult;

public interface IDictionary {

    void loadVocabularies(String filePath) throws DictionaryException;

    void loadDefaultDictionary() throws DictionaryException;

    List<String> getEnglishWords() throws DictionaryException;

    List<String> getIndonesianWords() throws DictionaryException;

    List<Vocabulary> getVocabularies() throws DictionaryException;

    SearchResult search(String word, Language language) throws DictionaryException;

    int size();
}
