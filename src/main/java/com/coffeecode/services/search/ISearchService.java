package com.coffeecode.services.search;

import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.observer.ISearchConfigurable;
import com.coffeecode.services.visualization.observer.SearchObserver;

public interface ISearchService extends ISearchConfigurable {

    SearchResult search(String word, List<Vocabulary> data, Language language);

    void setObserver(SearchObserver observer);
}
