package com.coffeecode.services.file;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.json.IJsonService;

public class FileService implements IFileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final String dictionaryPath;
    private final FileValidator validator;
    private final IJsonService jsonService;

    public FileService(String dictionaryPath, long maxFileSize, IJsonService jsonService) {
        this.dictionaryPath = dictionaryPath;
        this.validator = new FileValidator(maxFileSize);
        this.jsonService = jsonService;
        logger.info("Initialized FileService with dictionary path: {}", dictionaryPath);
    }

    @Override
    public List<Vocabulary> loadVocabularies(String filePath) throws DictionaryException {
        logger.info("Loading vocabularies from: {}", filePath);
        File file = validator.validateAndGetFile(filePath);
        return jsonService.parseVocabularyFile(file);
    }

    @Override
    public List<Vocabulary> loadDefaultDictionary() throws DictionaryException {
        logger.info("Loading default dictionary from: {}", dictionaryPath);
        return loadVocabularies(dictionaryPath);
    }
}
