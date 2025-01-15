package com.coffeecode.search;

import java.util.Comparator;
import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;

public class BinarySearch implements SearchStrategy {

    @Override
    public SearchResult search(String word, List<Vocabulary> data, Language language) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Search word cannot be empty");
        }

        List<Vocabulary> sortedData = data.stream()
                .sorted(Comparator.comparing(language::getWord, String.CASE_INSENSITIVE_ORDER))
                .toList();

        if (sortedData.isEmpty()) {
            return new SearchResult(false, word, "");
        }

        int left = 0;
        int right = sortedData.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Vocabulary current = sortedData.get(mid);
            String currentWord = language.getWord(current);

            int comparison = currentWord.compareToIgnoreCase(word);

            if (comparison == 0) {
                return new SearchResult(
                        true,
                        currentWord,
                        language.getOpposite().getWord(current)
                );
            }

            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new SearchResult(false, word, "");
    }
}
