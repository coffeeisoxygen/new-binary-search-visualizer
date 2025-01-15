package com.coffeecode.services.search.result;

public record SearchResult(
        boolean found,
        String word,
        String translation,
        int comparisons,
        double timeMs) {

    public SearchResult     {
        if (word == null) {
            word = "";
        }
        if (translation == null) {
            translation = "";
        }
    }
}
