package com.coffeecode.services.visualization.observer;

import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.SearchStepInfo;

public class DefaultSearchObserver implements SearchObserver {

    @Override
    public void onSearchStep(SearchStepInfo info) {
        // No-op for backward compatibility
    }

    @Override
    public void onSearchComplete(SearchResult result, int comparisons, double timeMs) {
        // No-op for backward compatibility
    }
}
