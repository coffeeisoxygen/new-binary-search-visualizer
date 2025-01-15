package com.coffeecode.view;

import com.coffeecode.tracker.SearchResult;
import com.coffeecode.tracker.SearchStep;

public class SearchResultFormatter {
    public static String format(SearchResult result) {
        StringBuilder sb = new StringBuilder();
        
        if (result.getSteps().isEmpty()) {
            return "No search steps recorded.";
        }

        sb.append("Search steps:\n");
        sb.append("------------\n");
        
        int stepCount = 1;
        for (SearchStep step : result.getSteps()) {
            sb.append(String.format("Step %-2d: [low: %-2d, mid: %-2d, high: %-2d] | Word: '%-15s' | Comparison: %d%n",
                    stepCount++, step.low(), step.mid(), step.high(),
                    step.currentWord(), step.comparison()));
        }
        
        sb.append("------------\n");
        sb.append(String.format("Result: %s (%s)",
                result.isFound() ? "Found" : "Not Found",
                result.isFound() ? result.getResult() : "No translation available"));
        
        return sb.toString();
    }
}
