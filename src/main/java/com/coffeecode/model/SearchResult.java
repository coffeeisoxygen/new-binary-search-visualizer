package com.coffeecode.model;

/*
 * this class is responsible for tracking the search result
 */
public class SearchResult {
    private final int low;
    private final int mid;
    private final int high;
    private final String result;
    private final boolean found;

    public SearchResult(int low, int mid, int high, String result, boolean found) {
        this.low = low;
        this.mid = mid;
        this.high = high;
        this.result = result;
        this.found = found;
    }

    // Getters
    public int getLow() {
        return low;
    }

    public int getMid() {
        return mid;
    }

    public int getHigh() {
        return high;
    }

    public String getResult() {
        return result;
    }

    public boolean isFound() {
        return found;
    }
}