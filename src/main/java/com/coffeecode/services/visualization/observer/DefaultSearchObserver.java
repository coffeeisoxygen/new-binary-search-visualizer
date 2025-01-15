package com.coffeecode.services.visualization.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.SearchStepInfo;

public class DefaultSearchObserver implements SearchObserver {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSearchObserver.class);

    @Override
    public void onSearchStep(SearchStepInfo info) {
        logger.debug("Search step {}: low[{}]>{} mid[{}]>{} high[{}]>{}",
                info.step(),
                info.low(), info.lowWord(),
                info.mid(), info.midWord(),
                info.high(), info.highWord()
        );
    }

    @Override
    public void onSearchComplete(SearchResult result, int comparisons, double timeMs) {
        logger.debug("Search completed: found={}, comparisons={}, time={}ms",
                result.found(), comparisons, timeMs);
    }
}
