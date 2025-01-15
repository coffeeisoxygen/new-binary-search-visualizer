package com.coffeecode.model;

/*
 * this class is responsible for Data Structure
 */
public record Vocabulary(String english, String indonesian) {
    public Vocabulary {
        if (english == null || english.isBlank()) {
            throw new IllegalArgumentException("English word cannot be empty");
        }
        if (indonesian == null || indonesian.isBlank()) {
            throw new IllegalArgumentException("Indonesian word cannot be empty");
        }
    }
}
