package com.coffeecode.search;

public record SearchResult(
        boolean found,
        String word,
        String translation) {

    public SearchResult   {
        word = (word != null) ? word : "";
        translation = (translation != null) ? translation : "";
    }
}
