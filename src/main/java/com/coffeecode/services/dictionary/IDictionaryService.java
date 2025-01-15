package com.coffeecode.services.dictionary;

import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.search.result.SearchResult;

public interface IDictionaryService {

    SearchResult search(String word, Language language);

    List<String> getWordsByLanguage(Language language);

    List<Vocabulary> getVocabularies();

    int size();

    boolean isInitialized();
}
