package com.coffeecode.services.search;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.search.strategy.BinarySearch;
import com.coffeecode.services.search.strategy.SearchStrategy;
import com.coffeecode.services.visualization.observer.DefaultSearchObserver;
import com.coffeecode.services.visualization.observer.ISearchConfigurable;
import com.coffeecode.services.visualization.observer.SearchObserver;

public class SearchService implements ISearchService, ISearchConfigurable {

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
    private final SearchStrategy strategy;
    private SearchObserver observer;

    public SearchService(SearchStrategy strategy) {
        this.strategy = strategy;
        this.observer = new DefaultSearchObserver();
        // Initialize strategy with default observer
        if (strategy instanceof BinarySearch binarySearch) {
            binarySearch.setObserver(this.observer);
        }
    }

    @Override
    public SearchResult search(String word, List<Vocabulary> data, Language language) {
        validateSearchParams(word, data, language);
        logger.debug("Searching for word: {} in {} dictionary", word, language);

        long startTime = System.nanoTime();
        SearchResult result = strategy.search(word, data, language);
        long endTime = System.nanoTime();

        logSearchResult(result, startTime, endTime);
        return result;
    }

    @Override
    public void setObserver(SearchObserver observer) {
        this.observer = observer != null ? observer : new DefaultSearchObserver();
        if (strategy instanceof BinarySearch binarySearch) {
            binarySearch.setObserver(this.observer);
        }
    }

    @Override
    public void configureSearch(SearchObserver observer) {
        this.observer = observer != null ? observer : new DefaultSearchObserver();
        // Ensure strategy gets the observer
        if (strategy instanceof BinarySearch binarySearch) {
            logger.debug("Configuring search observer: {}", observer.getClass().getSimpleName());
            binarySearch.setObserver(this.observer);
        }
    }

    private void validateSearchParams(String word, List<Vocabulary> data, Language language) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Search word cannot be empty");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data list cannot be null");
        }
        if (language == null) {
            throw new IllegalArgumentException("Language cannot be null");
        }
    }

    private void logSearchResult(SearchResult result, long startTime, long endTime) {
        double duration = (endTime - startTime) / 1_000_000.0;
        logger.debug("Search completed in {} ms. Found: {}", duration, result.found());
    }
}
