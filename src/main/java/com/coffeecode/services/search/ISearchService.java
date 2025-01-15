package com.coffeecode.services.search;

import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.search.result.SearchResult;

public interface ISearchService {

    SearchResult search(String word, List<Vocabulary> data, Language language);
}
