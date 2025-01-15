package com.coffeecode.services.json;

import java.io.File;
import java.util.List;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Vocabulary;

public interface IJsonService {

    List<Vocabulary> parseVocabularyFile(File file) throws DictionaryException;

}
