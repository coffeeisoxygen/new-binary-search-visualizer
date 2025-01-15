package com.coffeecode.services.visualization.formater;

import java.util.List;

import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.observer.SearchStepInfo;

public class SearchStepFormatter {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public String formatWelcomeScreen(List<String> englishWords, List<String> indonesianWords) {
        StringBuilder sb = new StringBuilder();
        sb.append(ANSI_BLUE)
                .append("================Welcome to Binary Search================\n")
                .append(ANSI_RESET);

        sb.append(ANSI_GREEN).append("English Words:\n").append(ANSI_RESET);
        formatWordList(sb, englishWords);

        sb.append(ANSI_GREEN).append("\nIndonesian Words:\n").append(ANSI_RESET);
        formatWordList(sb, indonesianWords);

        return sb.toString();
    }

    private void formatWordList(StringBuilder sb, List<String> words) {
        int columns = 5;
        int counter = 0;

        for (String word : words) {
            sb.append(String.format("%-15s", word));
            counter++;
            if (counter % columns == 0) {
                sb.append("\n");
            }
        }
        if (counter % columns != 0) {
            sb.append("\n");
        }
    }

    public String formatInputPrompt() {
        return String.format("%sEnter search word (or 'quit' to exit): %s",
                ANSI_YELLOW, ANSI_RESET);
    }

    public String formatLanguagePrompt() {
        return String.format("""
            %sSelect language:
            1. English
            2. Indonesian
            (or 'quit' to exit): %s""",
                ANSI_YELLOW, ANSI_RESET);
    }

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
