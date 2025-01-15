package com.coffeecode.tracker;

/**
 * Represents a single step in the binary search process.
 * Records the position indices and comparison result for each iteration.
 */
public record SearchStep(
        int low,
        int mid,
        int high,
        String currentWord,
        int comparison) {
    
    public SearchStep {
        if (currentWord == null) {
            throw new IllegalArgumentException("Current word cannot be null");
        }
        if (low > high) {
            throw new IllegalArgumentException("Low index cannot be greater than high index");
        }
        if (mid < low || mid > high) {
            throw new IllegalArgumentException("Mid index must be between low and high");
        }
    }
}