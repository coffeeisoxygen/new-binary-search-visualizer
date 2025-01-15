package com.coffeecode.model;

import java.util.List;

import com.coffeecode.exception.DictionaryException;

public interface IFileService {

    List<Vocabulary> loadDefaultDictionary() throws DictionaryException;

    List<Vocabulary> loadVocabularies(String filePath) throws DictionaryException;
}
