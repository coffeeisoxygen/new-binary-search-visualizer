package com.coffeecode.tracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchTracker {
    private final List<SearchStep> steps = new ArrayList<>();

    public void trackStep(int low, int mid, int high, String currentWord, int comparison) {
        steps.add(new SearchStep(low, mid, high, currentWord, comparison));
    }

    public List<SearchStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    public void clear() {
        steps.clear();
    }
}
