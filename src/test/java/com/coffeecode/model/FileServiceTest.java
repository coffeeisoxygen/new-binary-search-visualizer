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
import com.fasterxml.jackson.databind.ObjectMapper;

class FileServiceTest {

    private FileService fileService;
    @TempDir
    File tempDir;
    private ObjectMapper objectMapper;
    private static final String VALID_JSON = """
        {
            "vocabulary": [
                {"english": "cat", "indonesian": "kucing"},
                {"english": "dog", "indonesian": "anjing"}
            ]
        }
        """;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        objectMapper = new ObjectMapper();
    }

    @Test
    void loadVocabularies_WithNullPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(DictionaryException.class,
                () -> fileService.loadVocabularies(null));
        assertEquals("File path cannot be empty", exception.getMessage());
    }

    @Test
    void loadVocabularies_WithEmptyPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(DictionaryException.class,
                () -> fileService.loadVocabularies("  "));
        assertEquals("File path cannot be empty", exception.getMessage());
    }

    @Test
    void loadVocabularies_WithInvalidPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(DictionaryException.class,
                () -> fileService.loadVocabularies("invalid/path/dict.json"));
        assertTrue(exception.getMessage().startsWith("File not found:"));
    }

    @Test
    void loadVocabularies_WithValidJson_ShouldReturnVocabularyList() throws IOException {
        // Create test file
        File testFile = new File(tempDir, "test-dict.json");
        Files.writeString(testFile.toPath(), VALID_JSON);

        // Test
        List<Vocabulary> result = fileService.loadVocabularies(testFile.getAbsolutePath());

        // Verify
        assertEquals(2, result.size());
        assertEquals("cat", result.get(0).english());
        assertEquals("kucing", result.get(0).indonesian());
    }

    @Test
    void loadVocabularies_WithInvalidJson_ShouldThrowException() throws IOException {
        // Create invalid JSON file
        File testFile = new File(tempDir, "invalid.json");
        Files.writeString(testFile.toPath(), "{\"vocabulary\": \"not-an-array\"}");

        DictionaryException exception = assertThrows(DictionaryException.class,
                () -> fileService.loadVocabularies(testFile.getAbsolutePath()));
        assertTrue(exception.getMessage().contains("Invalid JSON structure"));
    }

    @Test
    void loadVocabularies_WithUnreadableFile_ShouldThrowException() throws IOException {
        // Create and make file unreadable
        File testFile = new File(tempDir, "unreadable.json");
        Files.writeString(testFile.toPath(), VALID_JSON);
        boolean canSetNotReadable = testFile.setReadable(false);

        if (canSetNotReadable) {
            DictionaryException exception = assertThrows(DictionaryException.class,
                    () -> fileService.loadVocabularies(testFile.getAbsolutePath()));
            assertTrue(exception.getMessage().contains("Cannot read file"));
        }
    }

    @Test
    void loadDefaultDictionary_ShouldThrowException_WhenFileNotFound() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadDefaultDictionary()
        );
        assertEquals("File not found: src/main/resources/vocabulary.json",
                exception.getMessage());
    }
}
