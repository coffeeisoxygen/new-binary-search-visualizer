package com.coffeecode.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;

public class FileService implements IFileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private static final long DEFAULT_MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final String DEFAULT_DICTIONARY_PATH = "src/main/resources/vocabulary.json";

    private final String dictionaryPath;
    private final long maxFileSize;
    private final IJsonService jsonService;

    public FileService() {
        this(DEFAULT_DICTIONARY_PATH, DEFAULT_MAX_FILE_SIZE, new JsonService());
    }

    public FileService(String dictionaryPath) {
        this(dictionaryPath, DEFAULT_MAX_FILE_SIZE, new JsonService());
    }

    public FileService(String dictionaryPath, long maxFileSize) {
        this(dictionaryPath, maxFileSize, new JsonService());
    }

    public FileService(String dictionaryPath, long maxFileSize, IJsonService jsonService) {
        this.dictionaryPath = dictionaryPath;
        this.maxFileSize = maxFileSize;
        this.jsonService = jsonService;
        logger.info("Initialized FileService with dictionary path: {}", dictionaryPath);
    }

    @Override
    public List<Vocabulary> loadDefaultDictionary() throws DictionaryException {
        logger.info("Loading default dictionary from: {}", dictionaryPath);
        return loadVocabularies(dictionaryPath);
    }

    @Override
    public List<Vocabulary> loadVocabularies(String filePath) throws DictionaryException {
        File file = validateAndGetFile(filePath);
        return jsonService.parseVocabularyFile(file);
    }

    private File validateAndGetFile(String filePath) {
        validateFilePath(filePath);
        File file = new File(filePath);
        validateFileExists(file);
        validateFileSize(file);
        validateFileReadable(file);
        validateFileExtension(file);
        validatePathSecurity(file);
        return file;
    }

    private void validateFilePath(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new DictionaryException(
                    ErrorCode.FILE_NOT_FOUND,
                    "File path cannot be empty"
            );
        }
    }

    private void validateFileExists(File file) {
        if (!file.exists()) {
            throw new DictionaryException(
                    ErrorCode.FILE_NOT_FOUND,
                    String.format("File not found at: %s", file.getPath())
            );
        }
    }

    private void validateFileSize(File file) {
        if (file.length() > maxFileSize) {
            throw new DictionaryException(
                    ErrorCode.FILE_TOO_LARGE,
                    String.format("File size exceeds maximum limit of %d MB", maxFileSize / (1024 * 1024))
            );
        }
    }

    private void validateFileReadable(File file) {
        if (!file.canRead()) {
            throw new DictionaryException(
                    ErrorCode.PERMISSION_DENIED,
                    String.format("Cannot read file: %s", file.getPath())
            );
        }
    }

    private void validateFileExtension(File file) {
        if (!file.getName().toLowerCase().endsWith(".json")) {
            throw new DictionaryException(
                    ErrorCode.INVALID_FILE_TYPE,
                    String.format("Invalid file type: %s (must be .json)", file.getName())
            );
        }
    }

    private void validatePathSecurity(File file) {
        try {
            String canonicalPath = file.getCanonicalPath();
            String allowedPath = new File(".").getCanonicalPath();

            if (!canonicalPath.startsWith(allowedPath)) {
                throw new DictionaryException(
                        ErrorCode.PERMISSION_DENIED,
                        "Access to file outside application directory is not allowed"
                );
            }
        } catch (IOException e) {
            throw new DictionaryException(
                    ErrorCode.PERMISSION_DENIED,
                    "Unable to validate file path security"
            );
        }
    }
}
