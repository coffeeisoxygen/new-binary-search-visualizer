package com.coffeecode.model;

import java.util.List;

import com.coffeecode.services.search.result.SearchResult;

public interface IDictionary {

    SearchResult search(String word, Language language);

    List<String> getWordsByLanguage(Language language);

    List<Vocabulary> getVocabularies();

    int size();
}
