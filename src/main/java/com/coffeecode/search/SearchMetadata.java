package com.coffeecode.search;

public class SearchMetadata {

    private final int comparisons;
    private final long searchTime;

    public SearchMetadata() {
        this(0, 0);
    }

    public SearchMetadata(int comparisons, long searchTime) {
        this.comparisons = comparisons;
        this.searchTime = searchTime;
    }

    public int getComparisons() {
        return comparisons;
    }

    public long getSearchTime() {
        return searchTime;
    }
}
