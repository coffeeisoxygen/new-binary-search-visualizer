package com.coffeecode.repository;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.model.Vocabulary;

@ExtendWith(MockitoExtension.class)
class JsonDictionaryRepositoryTest {
    private JsonDictionaryRepository repository;
    private static final String TEST_FILE = "src/test/resources/test-vocabulary.json";

    @BeforeEach
    void setup() {
        repository = new JsonDictionaryRepository();
    }

    @Test
    void shouldLoadVocabulariesFromValidJson() throws IOException {
        List<Vocabulary> vocabularies = repository.loadVocabularies(TEST_FILE);
        assertNotNull(vocabularies);
        assertFalse(vocabularies.isEmpty());
    }

    @Test
    void shouldThrowExceptionForInvalidPath() {
        assertThrows(IOException.class, () -> repository.loadVocabularies("invalid/path.json"));
    }
}
