package com.coffeecode.services.file;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;

public class FileService implements IFileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final String dictionaryPath;
    private final FileValidator validator;

    public FileService(String dictionaryPath, long maxFileSize) {
        this.dictionaryPath = dictionaryPath;
        this.validator = new FileValidator(maxFileSize);
        logger.info("Initialized FileService with dictionary path: {}", dictionaryPath);
    }

    @Override
    public File getDefaultDictionaryFile() throws DictionaryException {
        logger.info("Getting default dictionary file: {}", dictionaryPath);
        return getFile(dictionaryPath);
    }

    @Override
    public File getFile(String filePath) throws DictionaryException {
        logger.info("Getting file: {}", filePath);
        return validator.validateAndGetFile(filePath);
    }
}
