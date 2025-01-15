package com.coffeecode.services.visualization.observer;

import com.coffeecode.services.search.result.SearchResult;

public interface SearchObserver {

    void onSearchStep(SearchStepInfo stepInfo);

    void onSearchComplete(SearchResult result, int totalComparisons, double totalTimeMs);
}
