package com.coffeecode.model;

import java.util.List;

/*
 * this class is responsible for tracking the search result
 */
public class SearchResult {
    private final int low;
    private final int mid;
    private final int high;
    private final String result;
    private final boolean found;
    private final List<SearchStep> steps;

    public SearchResult(int low, int mid, int high, String result, boolean found, List<SearchStep> steps) {
        this.low = low;
        this.mid = mid;
        this.high = high;
        this.result = result;
        this.found = found;
        this.steps = steps;
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

    public List<SearchStep> getSteps() {
        return steps;
    }

    public void displaySteps() {
        System.out.println("Search steps:");
        for (int i = 0; i < steps.size(); i++) {
            SearchStep step = steps.get(i);
            System.out.printf("Step %d: low=%d, mid=%d, high=%d, word='%s', comparison=%d%n",
                    i + 1, step.low(), step.mid(), step.high(),
                    step.currentWord(), step.comparison());
        }
    }
}