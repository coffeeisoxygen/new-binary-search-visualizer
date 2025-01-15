package com.coffeecode.services.visualization;

import com.coffeecode.model.Language;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.formater.SearchStepFormatter;
import com.coffeecode.services.visualization.observer.SearchObserver;
import com.coffeecode.services.visualization.observer.SearchStepInfo;

public class SearchVisualization implements SearchObserver {
    private final SearchStepFormatter formatter;
    private final Language searchLanguage;
    private final String searchWord;
    private long startTime;
    
    public SearchVisualization(String word, Language language) {
        this.formatter = new SearchStepFormatter();
        this.searchWord = word;
        this.searchLanguage = language;
        System.out.println(formatter.formatSearchHeader(word, language.toString()));
    }
    
    @Override
    public void onSearchStep(SearchStepInfo info) {
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
