package com.coffeecode.tracker;

import java.util.Collections;
import java.util.List;

/**
 * Tracks and stores the results of a binary search operation.
 * Includes final positions, result word, search status, and step-by-step history.
 */
public class SearchResult {
    private final int low;
    private final int mid;
    private final int high;
    private final String result;
    private final boolean found;
    private final List<SearchStep> steps;

    private SearchResult(Builder builder) {
        this.low = builder.low;
        this.mid = builder.mid;
        this.high = builder.high;
        this.result = builder.result;
        this.found = builder.found;
        this.steps = Collections.unmodifiableList(builder.steps);
    }

    // Getters only
    public int getLow() { return low; }
    public int getMid() { return mid; }
    public int getHigh() { return high; }
    public String getResult() { return result; }
    public boolean isFound() { return found; }
    public List<SearchStep> getSteps() { return steps; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int low;
        private int mid;
        private int high;
        private String result = "";
        private boolean found;
        private List<SearchStep> steps = List.of();

        public Builder indices(int low, int mid, int high) {
            this.low = low;
            this.mid = mid;
            this.high = high;
            return this;
        }

        public Builder result(String result) {
            this.result = result != null ? result : "";
            return this;
        }

        public Builder found(boolean found) {
            this.found = found;
            return this;
        }

        public Builder steps(List<SearchStep> steps) {
            this.steps = steps != null ? steps : List.of();
            return this;
        }

        public SearchResult build() {
            return new SearchResult(this);
        }
    }
}