package com.coffeecode.services.visualization.observer;

public record SearchStepInfo(
        int step,
        int low,
        String lowWord,
        int mid,
        String midWord,
        int high,
        String highWord,
        int comparisons,
        double timeMs) {

}
