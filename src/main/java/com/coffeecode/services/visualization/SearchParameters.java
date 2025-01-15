package com.coffeecode.services.visualization;

import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;

public class SearchParameters {

    private final List<Vocabulary> data;
    private final Language language;
    private final int left;
    private final int mid;
    private final int right;
    private final int comparisons;
    private final long startTime;

    // Constructor
    public SearchParameters(List<Vocabulary> data, Language language,
            int left, int mid, int right,
            int comparisons, long startTime) {
        this.data = data;
        this.language = language;
        this.left = left;
        this.mid = mid;
        this.right = right;
        this.comparisons = comparisons;
        this.startTime = startTime;
    }

    // Getters
    public List<Vocabulary> getData() {
        return data;
    }

    public Language getLanguage() {
        return language;
    }

    public int getLeft() {
        return left;
    }

    public int getMid() {
        return mid;
    }

    public int getRight() {
        return right;
    }

    public int getComparisons() {
        return comparisons;
    }

    public long getStartTime() {
        return startTime;
    }

}
