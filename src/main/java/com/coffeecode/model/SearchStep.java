package com.coffeecode.model;

public record SearchStep(
        int low,
        int mid,
        int high,
        String currentWord,
        int comparison) {
}