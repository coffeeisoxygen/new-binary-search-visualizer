package com.coffeecode.services.file;

import java.util.List;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Vocabulary;

public interface IFileService {

    List<Vocabulary> loadDefaultDictionary() throws DictionaryException;

    List<Vocabulary> loadVocabularies(String filePath) throws DictionaryException;
}
