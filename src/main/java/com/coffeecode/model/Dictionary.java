package com.coffeecode.model;

import java.util.ArrayList;
import java.util.List;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
import com.coffeecode.search.SearchResult;
import com.coffeecode.search.SearchStrategy;
import com.coffeecode.services.file.IFileService;

public class Dictionary implements IDictionary {

    private List<Vocabulary> vocabularies;
    private List<Vocabulary> englishSorted;
    private List<Vocabulary> indonesianSorted;
    private int size;
    private boolean isInitialized;
    private final SearchStrategy searchStrategy;
    private final IFileService fileService;

    public Dictionary(SearchStrategy searchStrategy, IFileService fileService) {
        this.searchStrategy = searchStrategy;
        this.fileService = fileService;
    }

    @Override
    public void loadDefaultDictionary() throws DictionaryException {
        this.vocabularies = new ArrayList<>(fileService.loadDefaultDictionary());
        this.isInitialized = false;
        initializeSortedLists();
    }

    @Override
    public void loadVocabularies(String filePath) throws DictionaryException {
        if (filePath == null || filePath.isBlank()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_WORD,
                    "File path cannot be empty"
            );
        }
        this.vocabularies = fileService.loadVocabularies(filePath);
        initializeSortedLists();
    }

    private void initializeSortedLists() {
        if (!isInitialized) {
            this.englishSorted = new ArrayList<>(sortByLanguage(vocabularies, Language.ENGLISH));
            this.indonesianSorted = new ArrayList<>(sortByLanguage(vocabularies, Language.INDONESIAN));
            this.size = vocabularies.size();
            this.isInitialized = true;
        }
    }

    private List<Vocabulary> sortByLanguage(List<Vocabulary> list, Language language) {
        return list.stream()
                .sorted((v1, v2) -> String.CASE_INSENSITIVE_ORDER.compare(
                language.getWord(v1),
                language.getWord(v2)))
                .toList();
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
        List<Vocabulary> searchList = (language == Language.ENGLISH) ? englishSorted : indonesianSorted;
        return searchStrategy.search(word, searchList, language);
    }

    private void validateDictionaryLoaded() throws DictionaryException {
        if (vocabularies == null) {
            throw new DictionaryException(
                    ErrorCode.DICTIONARY_NOT_LOADED,
                    "Dictionary not loaded. Call loadDictionary() first"
            );
        }
    }

    @Override
    public int size() {
        return size;
    }
}
