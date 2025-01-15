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

class FileServiceTest {

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

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    @Test
    void loadVocabularies_WithNullPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadVocabularies(null)
        );
        assertEquals(
                "Dictionary Error: File path cannot be empty",
                exception.getMessage()
        );
    }

    @Test
    void loadVocabularies_WithEmptyPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadVocabularies("  ")
        );
        assertEquals(
                "Dictionary Error: File path cannot be empty",
                exception.getMessage()
        );
    }

    @Test
    void loadVocabularies_WithValidJson_ShouldReturnVocabularyList() throws IOException {
        File testFile = new File(tempDir, "test-dict.json");
        Files.writeString(testFile.toPath(), VALID_JSON);

        List<Vocabulary> result = fileService.loadVocabularies(testFile.getAbsolutePath());

        assertEquals(2, result.size());
        assertEquals("cat", result.get(0).english());
        assertEquals("kucing", result.get(0).indonesian());
    }

    @Test
    void loadDefaultDictionary_ShouldThrowException_WhenFileNotFound() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadDefaultDictionary()
        );
        assertTrue(exception.getMessage().startsWith("Dictionary Error: File not found:"));
        assertTrue(exception.getMessage().endsWith("vocabulary.json"));
    }

    @Test
    void loadVocabularies_WithInvalidJson_ShouldThrowException() throws IOException {
        File testFile = new File(tempDir, "invalid.json");
        Files.writeString(testFile.toPath(), "{\"vocabulary\": \"not-an-array\"}");

        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadVocabularies(testFile.getAbsolutePath())
        );
        assertTrue(exception.getMessage().contains("Dictionary Error: Invalid JSON structure"));
    }
}
