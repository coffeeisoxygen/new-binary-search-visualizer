package com.coffeecode.services.visualization;

import com.coffeecode.services.visualization.observer.SearchObserver;

public interface ISearchConfigurable {
    void configureSearch(SearchObserver observer);
}