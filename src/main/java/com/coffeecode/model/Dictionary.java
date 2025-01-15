package com.coffeecode.model;

import java.io.IOException;
import java.util.List;
import com.coffeecode.search.SearchResult;
import com.coffeecode.search.SearchStrategy;

public class Dictionary implements IDictionary {

    private List<Vocabulary> vocabularies;
    private final SearchStrategy searchStrategy;
    private final FileService fileService;

    public Dictionary(SearchStrategy searchStrategy, FileService fileService) {
        this.searchStrategy = searchStrategy;
        this.fileService = fileService;
    }

    @Override
    public void loadDefaultDictionary() throws IOException {
        this.vocabularies = fileService.loadDefaultDictionary();
    }

    @Override
    public void loadVocabularies(String filePath) throws IOException {
        this.vocabularies = fileService.loadVocabularies(filePath);
    }

    @Override
    public List<String> getEnglishWords() {
        return getWordsByLanguage(Language.ENGLISH);
    }

    @Override
    public List<String> getIndonesianWords() {
        return getWordsByLanguage(Language.INDONESIAN);
    }

    private List<String> getWordsByLanguage(Language language) {
        return vocabularies.stream()
                .map(language::getWord)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }

    @Override
    public List<Vocabulary> getVocabularies() {
        return vocabularies;
    }

    @Override
    public SearchResult search(String word, Language language) {
        return searchStrategy.search(word, vocabularies, language);
    }
}
