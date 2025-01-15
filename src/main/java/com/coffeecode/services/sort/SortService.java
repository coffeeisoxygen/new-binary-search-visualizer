package com.coffeecode.services.sort;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;

public class SortService implements ISortService {

    private static final Logger logger = LoggerFactory.getLogger(SortService.class);
    private final VocabularySorter sorter;

    public SortService() {
        this.sorter = new VocabularySorter();
    }

    @Override
    public List<Vocabulary> sortByLanguage(List<Vocabulary> vocabularies, Language language) {
        logger.debug("Sorting {} vocabularies by {}", vocabularies.size(), language);
        long startTime = System.nanoTime();

        List<Vocabulary> sorted = sorter.sort(vocabularies, language);

        long endTime = System.nanoTime();
        logger.debug("Sorting completed in {} ms", (endTime - startTime) / 1_000_000.0);

        return sorted;
    }
}
