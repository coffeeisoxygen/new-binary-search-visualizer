package com.coffeecode.model;

import com.coffeecode.exception.DictionaryException;
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
    private static final String ERR_FILE_NOT_FOUND = "File not found: %s";
    private static final String ERR_FILE_NOT_READABLE = "Cannot read file: %s";
    private static final String ERR_INVALID_JSON = "Invalid JSON structure: %s";
    private static final String ERR_EMPTY_PATH = "File path cannot be empty";

    private final ObjectMapper objectMapper;

    public FileService() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Vocabulary> loadDefaultDictionary() throws DictionaryException {
        logger.info("Loading default dictionary from: {}", DEFAULT_DICTIONARY_PATH);
        return loadVocabularies(DEFAULT_DICTIONARY_PATH);
    }

    @Override
    public List<Vocabulary> loadVocabularies(String filePath) throws DictionaryException {
        File file = validateAndGetFile(filePath);
        JsonNode vocabularyArray = parseAndValidateJson(file);
        return convertToVocabularyList(vocabularyArray, filePath);
    }

    private File validateAndGetFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new DictionaryException(ERR_EMPTY_PATH);
        }

        File file = new File(filePath);
        if (!file.exists()) {
            String absolutePath = file.getAbsolutePath();
            logger.error("File not found at path: {}", absolutePath);
            throw new DictionaryException(String.format(ERR_FILE_NOT_FOUND, absolutePath));
        }
        if (!file.canRead()) {
            throw new DictionaryException(String.format(ERR_FILE_NOT_READABLE, file.getAbsolutePath()));
        }
        return file;
    }

    private JsonNode parseAndValidateJson(File file) {
        try {
            JsonNode root = objectMapper.readTree(file);
            JsonNode vocabularyArray = root.get("vocabulary");

            if (vocabularyArray == null || !vocabularyArray.isArray()) {
                throw new DictionaryException(String.format(ERR_INVALID_JSON, "missing or invalid vocabulary array"));
            }

            return vocabularyArray;
        } catch (IOException e) {
            throw new DictionaryException(String.format("Failed to parse dictionary file at %s", file.getAbsolutePath()), e);
        }
    }

    private List<Vocabulary> convertToVocabularyList(JsonNode vocabularyArray, String filePath) {
        try {
            return objectMapper.convertValue(vocabularyArray, new TypeReference<List<Vocabulary>>() {
            });
        } catch (IllegalArgumentException e) {
            throw new DictionaryException("Failed to convert JSON to vocabulary list", e);
        }
    }
}
