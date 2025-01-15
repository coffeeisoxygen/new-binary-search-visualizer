package com.coffeecode.model;

import java.io.IOException;
import java.util.List;
import com.coffeecode.search.SearchResult;

public interface IDictionary {

    void loadVocabularies(String filePath) throws IOException;

    void loadDefaultDictionary() throws IOException;

    List<String> getEnglishWords();

    List<String> getIndonesianWords();

    List<Vocabulary> getVocabularies();

    SearchResult search(String word, Language language);
}
