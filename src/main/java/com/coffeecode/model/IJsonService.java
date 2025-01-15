package com.coffeecode.model;

import java.io.File;
import java.util.List;

import com.coffeecode.exception.DictionaryException;

public interface IJsonService {

    List<Vocabulary> parseVocabularyFile(File file) throws DictionaryException;

}
