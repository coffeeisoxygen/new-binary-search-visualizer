package com.coffeecode.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ExceptionMessages;

class FileServiceTest {

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
    void loadVocabularies_WithValidJson_ShouldReturnVocabularyList() throws IOException {
        File testFile = createTestFile("valid.json", VALID_JSON);
        List<Vocabulary> result = fileService.loadVocabularies(testFile.getAbsolutePath());

        assertEquals(2, result.size());
        assertVocabulary(result.get(0), "cat", "kucing");
        assertVocabulary(result.get(1), "dog", "anjing");
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
        var exception = assertThrows(
            DictionaryException.class,
            () -> fileService.loadDefaultDictionary()
        );
        assertEquals(
            String.format(ExceptionMessages.ERR_DEFAULT_DICT_NOT_FOUND, DEFAULT_DICTIONARY_PATH),
            exception.getMessage()
        );
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
