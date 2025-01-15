package com.coffeecode.model;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ExceptionMessages;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileService implements IFileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private static final String DEFAULT_DICTIONARY_PATH = "src/main/resources/vocabulary.json";
    private final String defaultDictionaryPath;
    private final ObjectMapper objectMapper;

    public FileService() {
        this(DEFAULT_DICTIONARY_PATH);
    }

    // Constructor for testing
    public FileService(String defaultDictionaryPath) {
        this.defaultDictionaryPath = defaultDictionaryPath;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Vocabulary> loadDefaultDictionary() throws DictionaryException {
        logger.info("Loading default dictionary from: {}", defaultDictionaryPath);
        return loadVocabularies(defaultDictionaryPath);
    }

    @Override
    public List<Vocabulary> loadVocabularies(String filePath) throws DictionaryException {
        File file = validateAndGetFile(filePath);
        JsonNode vocabularyArray = parseAndValidateJson(file);
        return convertToVocabularyList(vocabularyArray, filePath);
    }

    private File validateAndGetFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new DictionaryException(ExceptionMessages.ERR_FILE_PATH_EMPTY);
        }

        File file = new File(filePath);
        if (!file.exists()) {
            String absolutePath = file.getAbsolutePath();
            String errorMessage = String.format(ExceptionMessages.ERR_FILE_NOT_FOUND, absolutePath);
            logger.error(errorMessage);
            throw new DictionaryException(errorMessage);
        }
        if (!file.canRead()) {
            throw new DictionaryException(String.format(ExceptionMessages.ERR_FILE_NOT_READABLE, file.getAbsolutePath()));
        }
        return file;
    }

    private JsonNode parseAndValidateJson(File file) {
        try {
            JsonNode root = objectMapper.readTree(file);
            JsonNode vocabularyArray = root.get("vocabulary");

            if (vocabularyArray == null || !vocabularyArray.isArray()) {
                throw new DictionaryException(String.format(ExceptionMessages.ERR_INVALID_JSON, file.getAbsolutePath()));
            }

            return vocabularyArray;
        } catch (IOException e) {
            throw new DictionaryException(String.format(ExceptionMessages.ERR_PARSE_JSON, file.getAbsolutePath()), e);
        }
    }

    private List<Vocabulary> convertToVocabularyList(JsonNode vocabularyArray, String filePath) {
        try {
            return objectMapper.convertValue(vocabularyArray, new TypeReference<List<Vocabulary>>() {
            });
        } catch (IllegalArgumentException e) {
            throw new DictionaryException(ExceptionMessages.ERR_CONVERT_JSON, e);
        }
    }
}
