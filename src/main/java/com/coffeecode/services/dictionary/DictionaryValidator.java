package com.coffeecode.services.dictionary;

import java.util.List;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.search.ISearchService;
import com.coffeecode.services.sort.ISortService;

public class DictionaryValidator {

    public void validateSearchWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_WORD,
                    "Search word cannot be empty or blank"
            );
        }
    }

    public void validateVocabularies(List<Vocabulary> vocabularies) {
        if (vocabularies == null || vocabularies.isEmpty()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_DICTIONARY,
                    "Vocabulary list cannot be null or empty"
            );
        }
    }

    public void validateServices(ISearchService searchService, ISortService sortService) {
        if (searchService == null || sortService == null) {
            throw new DictionaryException(
                    ErrorCode.INVALID_CONFIGURATION,
                    "Search and Sort services cannot be null"
            );
        }
    }

    public void validateDictionaryLoaded(boolean isInitialized) {
        if (!isInitialized) {
            throw new DictionaryException(
                    ErrorCode.DICTIONARY_NOT_LOADED,
                    "Dictionary not loaded. Please initialize first."
            );
        }
    }
}
