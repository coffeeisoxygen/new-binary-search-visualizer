package com.coffeecode.viewmodel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Language;
import com.coffeecode.services.dictionary.IDictionaryService;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.observer.ISearchConfigurable;
import com.coffeecode.services.visualization.observer.SearchObserver;

public class DictionaryViewModel implements ISearchConfigurable {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryViewModel.class);
    private final IDictionaryService dictionaryService;

    public DictionaryViewModel(IDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public SearchResult search(String word, Language language) {
        try {
            return dictionaryService.search(word, language);
        } catch (DictionaryException e) {
            logger.error("Search failed: {}", e.getMessage());
            throw e;
        }
    }

    public List<String> getWordsByLanguage(Language language) {
        return dictionaryService.getWordsByLanguage(language);
    }

    public boolean isLoaded() {
        return dictionaryService.isInitialized();
    }

    @Override
    public void configureSearch(SearchObserver observer) {
        if (dictionaryService instanceof ISearchConfigurable configurable) {
            configurable.configureSearch(observer);
        }
    }
}
