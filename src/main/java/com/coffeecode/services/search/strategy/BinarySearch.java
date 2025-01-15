package com.coffeecode.services.search.strategy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.observer.DefaultSearchObserver;
import com.coffeecode.services.visualization.observer.SearchObserver;
import com.coffeecode.services.visualization.observer.SearchStepInfo;

public class BinarySearch implements SearchStrategy {

    private static final Logger logger = LoggerFactory.getLogger(BinarySearch.class);
    private final SearchObserver observer;

    public BinarySearch() {
        this(new DefaultSearchObserver());
    }

    public BinarySearch(SearchObserver observer) {
        this.observer = observer != null ? observer : new DefaultSearchObserver();
    }

    @Override
    public SearchResult search(String word, List<Vocabulary> data, Language language) {
        if (data.isEmpty()) {
            logger.debug("Empty dictionary, returning not found for: {}", word);
            return createNotFoundResult(word, 0, System.nanoTime());
        }

        logger.debug("Starting binary search for '{}' in {} dictionary", word, language);
        return performBinarySearch(word, data, language);
    }

    private SearchResult performBinarySearch(String word, List<Vocabulary> data, Language language) {
        int left = 0;
        int right = data.size() - 1;
        int comparisons = 0;
        long startTime = System.nanoTime();

        while (left <= right) {
            comparisons++;
            int mid = left + (right - left) / 2;
            Vocabulary current = data.get(mid);
            String currentWord = language.getWord(current);

            observer.onSearchStep(new SearchStepInfo(
                    comparisons,
                    left, language.getWord(data.get(left)),
                    mid, currentWord,
                    right, language.getWord(data.get(right)),
                    comparisons,
                    (System.nanoTime() - startTime) / 1_000_000.0
            ));

            int comparison = currentWord.compareToIgnoreCase(word);
            if (comparison == 0) {
                return createFoundResult(current, language, comparisons, startTime);
            }
            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return createNotFoundResult(word, comparisons, startTime);
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
