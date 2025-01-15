package com.coffeecode.services;

import java.util.Comparator;
import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.SearchResult;
import com.coffeecode.model.Vocabulary;

public class BinarySearch {
    private final List<Vocabulary> vocabularies;
    private final Language language;

    public BinarySearch(List<Vocabulary> vocabularies, Language language) {
        this.language = language;
        this.vocabularies = switch (language) {
            case ENGLISH -> vocabularies.stream()
                    .sorted(Comparator.comparing(Vocabulary::english, String.CASE_INSENSITIVE_ORDER))
                    .toList();
            case INDONESIAN -> vocabularies.stream()
                    .sorted(Comparator.comparing(Vocabulary::indonesian, String.CASE_INSENSITIVE_ORDER))
                    .toList();
        };
    }

    public SearchResult search(String word) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Search word cannot be empty");
        }

        int left = 0;
        int right = vocabularies.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String currentWord = switch (language) {
                case ENGLISH -> vocabularies.get(mid).english();
                case INDONESIAN -> vocabularies.get(mid).indonesian();
            };

            int comparison = currentWord.compareToIgnoreCase(word);

            if (comparison == 0) {
                String result = switch (language) {
                    case ENGLISH -> vocabularies.get(mid).indonesian();
                    case INDONESIAN -> vocabularies.get(mid).english();
                };
                return new SearchResult(left, mid, right, result, true);
            }

            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new SearchResult(left, (left + right) / 2, right, "", false);
    }
}