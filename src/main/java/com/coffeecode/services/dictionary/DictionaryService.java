package com.coffeecode.services.dictionary;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Dictionary;
import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.search.result.SearchResult;

public class DictionaryService implements IDictionaryService {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);

    private final Dictionary dictionary;
    private final DictionaryValidator validator;
    private boolean isInitialized;

    public DictionaryService(Dictionary dictionary) {
        this.validator = new DictionaryValidator();
        this.dictionary = dictionary;
        this.isInitialized = true;
        logger.info("Dictionary service initialized");
    }

    @Override
    public SearchResult search(String word, Language language) {
        validator.validateSearchWord(word);
        validator.validateDictionaryLoaded(isInitialized);
        return dictionary.search(word.trim(), language);
    }

    @Override
    public List<String> getWordsByLanguage(Language language) {
        validator.validateDictionaryLoaded(isInitialized);
        return dictionary.getWordsByLanguage(language);
    }

    @Override
    public List<Vocabulary> getVocabularies() {
        validator.validateDictionaryLoaded(isInitialized);
        return dictionary.getVocabularies();
    }

    @Override
    public int size() {
        validator.validateDictionaryLoaded(isInitialized);
        return dictionary.size();
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

}
