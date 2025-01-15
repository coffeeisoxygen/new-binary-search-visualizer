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

    private SearchObserver observer;
    private static final Logger logger = LoggerFactory.getLogger(BinarySearch.class);

    public BinarySearch() {
        this(new DefaultSearchObserver());
    }

    public BinarySearch(SearchObserver observer) {
        this.observer = observer != null ? observer : new DefaultSearchObserver();
    }

    @Override
    public void setObserver(SearchObserver observer) {
        this.observer = observer != null ? observer : new DefaultSearchObserver();
    }

    @Override
    public SearchResult search(String word, List<Vocabulary> data, Language language) {
        long startTime = System.nanoTime();
        int comparisons = 0;

        if (data.isEmpty()) {
            logger.debug("Empty dictionary, returning not found for: {}", word);
            return createNotFoundResult(word, comparisons, startTime);
        }

        return performBinarySearch(word, new SearchParameters(
                data, language, 0, 0, data.size() - 1, 0, startTime
        ));
    }

    private SearchResult performBinarySearch(String word, SearchParameters params) {
        int left = params.getLeft();
        int right = params.getRight();
        int comparisons = params.getComparisons();
        long startTime = params.getStartTime();

        while (left <= right) {
            comparisons++;
            int mid = left + (right - left) / 2;
            Vocabulary current = params.getData().get(mid);

            // Notify observer of current step
            observer.onSearchStep(new SearchStepInfo(
                    comparisons,
                    left, params.getLanguage().getWord(params.getData().get(left)),
                    mid, params.getLanguage().getWord(current),
                    right, params.getLanguage().getWord(params.getData().get(right)),
                    comparisons,
                    (System.nanoTime() - startTime) / 1_000_000.0
            ));

            int comparison = params.getLanguage().getWord(current).compareToIgnoreCase(word);
            if (comparison == 0) {
                SearchResult result = createFoundResult(current, params.getLanguage(), comparisons, startTime);
                observer.onSearchComplete(result, comparisons, result.timeMs());
                return result;
            }
            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        SearchResult result = createNotFoundResult(word, comparisons, startTime);
        observer.onSearchComplete(result, comparisons, result.timeMs());
        return result;
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

    private SearchResult createNotFoundResult(String word,
            int comparisons, long startTime) {
        return new SearchResult(
                false,
                word,
                "",
                comparisons,
                (System.nanoTime() - startTime) / 1_000_000.0
        );
    }
}
