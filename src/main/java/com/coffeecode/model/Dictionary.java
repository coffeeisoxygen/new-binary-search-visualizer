package com.coffeecode.model;

import java.util.List;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
import com.coffeecode.search.SearchResult;
import com.coffeecode.search.SearchStrategy;

public class Dictionary implements IDictionary {

    private List<Vocabulary> vocabularies;
    private final SearchStrategy searchStrategy;
    private final IFileService fileService;

    public Dictionary(SearchStrategy searchStrategy, IFileService fileService) {
        this.searchStrategy = searchStrategy;
        this.fileService = fileService;
    }

    @Override
    public void loadDefaultDictionary() throws DictionaryException {
        this.vocabularies = sortVocabularies(fileService.loadDefaultDictionary());
    }

    @Override
    public void loadVocabularies(String filePath) throws DictionaryException {
        if (filePath == null || filePath.isBlank()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_WORD,
                    "File path cannot be empty"
            );
        }
        this.vocabularies = sortVocabularies(fileService.loadVocabularies(filePath));
    }

    @Override
    public List<String> getEnglishWords() throws DictionaryException {
        validateDictionaryLoaded();
        return getWordsByLanguage(Language.ENGLISH);
    }

    @Override
    public List<String> getIndonesianWords() throws DictionaryException {
        validateDictionaryLoaded();
        return getWordsByLanguage(Language.INDONESIAN);
    }

    private List<String> getWordsByLanguage(Language language) {
        return vocabularies.stream()
                .map(language::getWord)
                .toList(); // Remove sorting here as list is already sorted
    }

    private List<Vocabulary> sortVocabularies(List<Vocabulary> unsorted) {
        return unsorted.stream()
                .sorted((v1, v2) -> String.CASE_INSENSITIVE_ORDER.compare(
                v1.english().toLowerCase(), v2.english().toLowerCase()))
                .toList();
    }

    @Override
    public List<Vocabulary> getVocabularies() throws DictionaryException {
        validateDictionaryLoaded();
        return vocabularies;
    }

    @Override
    public SearchResult search(String word, Language language) throws DictionaryException {
        validateDictionaryLoaded();
        if (word == null || word.isBlank()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_WORD,
                    "Search word cannot be empty"
            );
        }
        return searchStrategy.search(word, vocabularies, language);
    }

    private void validateDictionaryLoaded() throws DictionaryException {
        if (vocabularies == null) {
            throw new DictionaryException(
                    ErrorCode.DICTIONARY_NOT_LOADED,
                    "Dictionary not loaded. Call loadDictionary() first"
            );
        }
    }
}
