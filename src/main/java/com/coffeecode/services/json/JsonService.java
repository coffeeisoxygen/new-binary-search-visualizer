package com.coffeecode.services.json;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Vocabulary;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonService implements IJsonService {

    private final JsonParsing parser;
    private final JsonValidator validator;
    private static final Logger logger = LoggerFactory.getLogger(JsonService.class);

    public JsonService() {
        this.parser = new JsonParsing();
        this.validator = new JsonValidator();
    }

    @Override
    public List<Vocabulary> parseVocabularyFile(File file) throws DictionaryException {
        JsonNode root = parser.parseFile(file);
        validator.validateJsonStructure(root);

        List<Vocabulary> vocabularies = parser.convertToVocabularyList(root.get("vocabulary"));
        validator.validateVocabularies(vocabularies);

        logger.info("Successfully parsed {} vocabularies from {}",
                vocabularies.size(), file.getPath());
        return vocabularies;
    }
}
