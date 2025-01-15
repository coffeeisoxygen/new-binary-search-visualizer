package com.coffeecode.services.visualization.formater;

import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.observer.SearchStepInfo;

public class SearchStepFormatter {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    
    public String formatSearchHeader(String word, String dictionary) {
        return String.format("""
            %s===============Search Process================
            Searching   : %s
            Dictionary  : %s
            =============================================%s
            """,
            ANSI_BLUE, word, dictionary, ANSI_RESET);
    }
    
    public String formatSearchStep(SearchStepInfo info) {
        return String.format("""
            %sStep %d:%s
            %slow[%d]>%s - mid[%d]>%s - high[%d]>%s%s
            """,
            ANSI_YELLOW, info.step(), ANSI_RESET,
            ANSI_GREEN,
            info.low(), info.lowWord(),
            info.mid(), info.midWord(),
            info.high(), info.highWord(),
            ANSI_RESET);
    }
    
    public String formatSearchSummary(SearchResult result, int comparisons, double timeMs) {
        return String.format("""
            %s============Search Summary===================
            Search completed in:
            - %d comparisons
            - %.2f ms
            Result: %s
            =============================================%s
            """,
            ANSI_BLUE,
            comparisons, timeMs,
            result.found() ? "Found: " + result.word() + " → " + result.translation() : "Not found: " + result.word(),
            ANSI_RESET);
    }
}
