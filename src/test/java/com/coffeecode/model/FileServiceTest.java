package com.coffeecode.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ExceptionMessages;

class FileServiceTest {

    private static final String TEST_RESOURCES = "src/test/resources/";
    private static final String TEST_VOCABULARY_PATH = TEST_RESOURCES + "test-vocabulary.json";
    private static final String EMPTY_VOCABULARY_PATH = TEST_RESOURCES + "empty-vocabulary.json";
    private static final String DEFAULT_DICTIONARY_PATH = "src/main/resources/vocabulary.json";
    private FileService fileService;
    @TempDir
    File tempDir;

    private static final String VALID_JSON = """
            {
                "vocabulary": [
                    {"english": "cat", "indonesian": "kucing"},
                    {"english": "dog", "indonesian": "anjing"}
                ]
            }
            """;

    private static final String INVALID_JSON = """
            {
                "vocabulary": "not-an-array"
            }
            """;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        // Ensure the default dictionary file does not exist before running tests
        File defaultFile = new File(DEFAULT_DICTIONARY_PATH);
        if (defaultFile.exists()) {
            defaultFile.delete();
        }
    }

    // File Validation Tests
    @Test
    void loadVocabularies_WithNullPath_ShouldThrowException() {
        var exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadVocabularies(null)
        );
        assertEquals(ExceptionMessages.ERR_FILE_PATH_EMPTY, exception.getMessage());
    }

    @Test
    void loadVocabularies_WithEmptyPath_ShouldThrowException() {
        var exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadVocabularies("  ")
        );
        assertEquals(ExceptionMessages.ERR_FILE_PATH_EMPTY, exception.getMessage());
    }

    @Test
    void loadVocabularies_WithNonexistentFile_ShouldThrowException() {
        String nonexistentPath = "nonexistent.json";
        var exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadVocabularies(nonexistentPath)
        );
        assertTrue(exception.getMessage().contains(nonexistentPath));
        assertTrue(exception.getMessage().startsWith(String.format(ExceptionMessages.ERR_FILE_NOT_FOUND, "")));
    }

    // JSON Parsing Tests
    @Test
    void loadVocabularies_WithValidJson_ShouldReturnVocabularyList() {
        List<Vocabulary> result = fileService.loadVocabularies(TEST_VOCABULARY_PATH);

        assertEquals(2, result.size());
        assertVocabulary(result.get(0), "hello", "halo");
        assertVocabulary(result.get(1), "world", "dunia");
    }

    @Test
    void loadVocabularies_WithInvalidJson_ShouldThrowException() throws IOException {
        File testFile = createTestFile("invalid.json", INVALID_JSON);
        String path = testFile.getAbsolutePath();
        var exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadVocabularies(path)
        );
        assertTrue(exception.getMessage().contains("Invalid JSON structure"));
    }

    // Default Dictionary Tests
    @Test
    void loadDefaultDictionary_WhenFileNotFound_ShouldThrowException() {
        // Verify file doesn't exist first
        File defaultFile = new File(DEFAULT_DICTIONARY_PATH);
        assertFalse(defaultFile.exists(), "Test requires file to not exist");

        var exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadDefaultDictionary()
        );

        String expectedMessage = String.format(ExceptionMessages.ERR_DEFAULT_DICT_NOT_FOUND, DEFAULT_DICTIONARY_PATH);
        String actualMessage = exception.getMessage();

        // Add debug output
        System.out.println("Expected: " + expectedMessage);
        System.out.println("Actual: " + actualMessage);

        assertEquals(expectedMessage, actualMessage);
    }

    // Helper Methods
    private File createTestFile(String filename, String content) throws IOException {
        File testFile = new File(tempDir, filename);
        Files.writeString(testFile.toPath(), content);
        return testFile;
    }

    private void assertVocabulary(Vocabulary vocabulary, String english, String indonesian) {
        assertEquals(english, vocabulary.english());
        assertEquals(indonesian, vocabulary.indonesian());
    }
}
