package com.coffeecode.services.visualization;

public record SearchStepInfo(
        int step,
        int low, String lowWord,
        int mid, String midWord,
        int high, String highWord,
        int comparisons,
        double timeMs) {

    public SearchStepInfo         {
        if (step < 1) {
            throw new IllegalArgumentException("Step must be positive");
        }
        if (low < 0) {
            throw new IllegalArgumentException("Low index must be non-negative");
        }
        if (high < low) {
            throw new IllegalArgumentException("High index must be >= low index");
        }
        if (mid < low || mid > high) {
            throw new IllegalArgumentException("Mid must be between low and high");
        }
        if (lowWord == null) {
            lowWord = "";
        }
        if (midWord == null) {
            midWord = "";
        }
        if (highWord == null) {
            highWord = "";
        }
    }
}
