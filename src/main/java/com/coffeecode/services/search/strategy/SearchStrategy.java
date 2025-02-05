package com.coffeecode.services.search.strategy;

import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.observer.SearchObserver;

public interface SearchStrategy {

    SearchResult search(String word, List<Vocabulary> data, Language language);

    void setObserver(SearchObserver observer);
}
