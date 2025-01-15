package com.coffeecode.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.services.search.ISearchService;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.sort.ISortService;

public class Dictionary implements IDictionary {

    private static final Logger logger = LoggerFactory.getLogger(Dictionary.class);

    private final List<Vocabulary> vocabularies;
    private final List<Vocabulary> englishSorted;
    private final List<Vocabulary> indonesianSorted;
    private final ISearchService searchService;
    private final ISortService sortService;
    private final int size;

    public Dictionary(ISearchService searchService,
            List<Vocabulary> vocabularies,
            ISortService sortService) {
        this.searchService = searchService;
        this.sortService = sortService;
        this.vocabularies = new ArrayList<>(vocabularies);
        this.englishSorted = new ArrayList<>(sortService.sortByLanguage(vocabularies, Language.ENGLISH));
        this.indonesianSorted = new ArrayList<>(sortService.sortByLanguage(vocabularies, Language.INDONESIAN));
        this.size = vocabularies.size();
        logger.info("Dictionary created with {} vocabularies", size);
    }

    @Override
    public SearchResult search(String word, Language language) {
        List<Vocabulary> searchList = (language == Language.ENGLISH) ? englishSorted : indonesianSorted;
        return searchService.search(word, searchList, language);
    }

    @Override
    public List<String> getWordsByLanguage(Language language) {
        List<Vocabulary> sortedList = (language == Language.ENGLISH)
                ? englishSorted
                : indonesianSorted;

        return sortedList.stream()
                .map(language::getWord)
                .toList();
    }

    @Override
    public List<Vocabulary> getVocabularies() {
        return List.copyOf(vocabularies);
    }

    @Override
    public int size() {
        return size;
    }
}
