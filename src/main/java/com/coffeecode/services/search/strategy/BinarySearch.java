package com.coffeecode.services.search.strategy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.SearchStepInfo;
import com.coffeecode.services.visualization.observer.DefaultSearchObserver;
import com.coffeecode.services.visualization.observer.SearchObserver;

public class BinarySearch implements SearchStrategy {

    private static final Logger logger = LoggerFactory.getLogger(BinarySearch.class);
    private SearchObserver observer;

    public BinarySearch() {
        this(new DefaultSearchObserver());
    }

    public BinarySearch(SearchObserver observer) {
        setObserver(observer);
    }

    @Override
    public void setObserver(SearchObserver observer) {
        logger.debug("Setting observer in BinarySearch: {}",
                observer != null ? observer.getClass().getSimpleName() : "null");
        this.observer = observer != null ? observer : new DefaultSearchObserver();
    }

    @Override
    public SearchResult search(String word, List<Vocabulary> data, Language language) {
        long startTime = System.nanoTime();
        int comparisons = 0;

        if (data.isEmpty()) {
            return createNotFoundResult(word, comparisons, startTime);
        }

        int left = 0;
        int right = data.size() - 1;

        while (left <= right) {
            comparisons++;
            int mid = left + (right - left) / 2;
            Vocabulary current = data.get(mid);

            // Notify observer for each step
            SearchStepInfo stepInfo = new SearchStepInfo(
                    comparisons,
                    left, language.getWord(data.get(left)),
                    mid, language.getWord(current),
                    right, language.getWord(data.get(right)),
                    comparisons,
                    (System.nanoTime() - startTime) / 1_000_000.0
            );
            notifySearchStep(stepInfo);

            int comparison = language.getWord(current).compareToIgnoreCase(word);
            if (comparison == 0) {
                SearchResult result = createFoundResult(current, language, comparisons, startTime);
                observer.onSearchComplete(result, comparisons,
                        (System.nanoTime() - startTime) / 1_000_000.0);
                return result;
            }

            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        SearchResult result = createNotFoundResult(word, comparisons, startTime);
        observer.onSearchComplete(result, comparisons,
                (System.nanoTime() - startTime) / 1_000_000.0);
        return result;
    }

    private void notifySearchStep(SearchStepInfo stepInfo) {
        if (observer != null) {
            observer.onSearchStep(stepInfo);
        }
    }

    private SearchResult createFoundResult(Vocabulary vocab, Language language,
            int comparisons, long startTime) {
        return new SearchResult(
                true,
                language.getWord(vocab),
                language.getOpposite().getWord(vocab),
                comparisons,
                (System.nanoTime() - startTime) / 1_000_000.0
        );
    }

    private SearchResult createNotFoundResult(String word, int comparisons, long startTime) {
        return new SearchResult(
                false,
                word,
                "",
                comparisons,
                (System.nanoTime() - startTime) / 1_000_000.0
        );
    }
}
