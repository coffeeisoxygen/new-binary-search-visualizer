package com.coffeecode.services.search.strategy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.search.result.SearchResult;

public class BinarySearch implements SearchStrategy {

    private static final Logger logger = LoggerFactory.getLogger(BinarySearch.class);

    @Override
    public SearchResult search(String word, List<Vocabulary> data, Language language) {
        if (data.isEmpty()) {
            logger.debug("Empty dictionary, returning not found for: {}", word);
            return new SearchResult(false, word, "");
        }

        logger.debug("Starting binary search for '{}' in {} dictionary", word, language);
        return performBinarySearch(word, data, language);
    }

    private SearchResult performBinarySearch(String word, List<Vocabulary> data, Language language) {
        int left = 0;
        int right = data.size() - 1;
        int comparisons = 0;

        while (left <= right) {
            comparisons++;
            int mid = left + (right - left) / 2;
            Vocabulary current = data.get(mid);
            String currentWord = language.getWord(current);

            int comparison = currentWord.compareToIgnoreCase(word);
            if (comparison == 0) {
                logger.debug("Word found after {} comparisons", comparisons);
                return createFoundResult(current, language);
            }
            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        logger.debug("Word not found after {} comparisons", comparisons);
        return createNotFoundResult(word);
    }

    private SearchResult createFoundResult(Vocabulary vocab, Language language) {
        return new SearchResult(
                true,
                language.getWord(vocab),
                language.getOpposite().getWord(vocab)
        );
    }

    private SearchResult createNotFoundResult(String word) {
        return new SearchResult(false, word, "");
    }
}
