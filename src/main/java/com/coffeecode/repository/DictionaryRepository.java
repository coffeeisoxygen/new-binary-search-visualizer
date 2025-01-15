package com.coffeecode.repository;

import java.io.IOException;
import java.util.List;

import com.coffeecode.model.Vocabulary;

public interface DictionaryRepository {
    List<Vocabulary> loadVocabularies(String filePath) throws IOException;
}
