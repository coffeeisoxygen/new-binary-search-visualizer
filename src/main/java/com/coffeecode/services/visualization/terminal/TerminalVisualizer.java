package com.coffeecode.services.visualization.terminal;

import java.util.Objects;

import com.coffeecode.model.Language;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.SearchStepInfo;
import com.coffeecode.services.visualization.observer.SearchObserver;

public class TerminalVisualizer implements SearchObserver {

    private final TerminalFormatter formatter;
    private final Language searchLanguage;
    private final String searchWord;
    private long startTime;

    public TerminalVisualizer(String word, Language language) {
        this.formatter = new TerminalFormatter();
        this.searchWord = Objects.requireNonNull(word, "Search word cannot be null");
        this.searchLanguage = Objects.requireNonNull(language, "Search language cannot be null");
        System.out.println(formatter.formatSearchHeader(word, language.toString()));
    }

    @Override
    public void onSearchStep(SearchStepInfo info) {
        if (info == null) {
            return;
        }

        if (info.step() == 1) {
            startTime = System.nanoTime();
        }
        System.out.println(formatter.formatSearchStep(info));
    }

    @Override
    public void onSearchComplete(SearchResult result, int comparisons, double timeMs) {
        double totalTime = (System.nanoTime() - startTime) / 1_000_000.0;
        System.out.println(formatter.formatSearchSummary(result, comparisons, totalTime));
    }
}
