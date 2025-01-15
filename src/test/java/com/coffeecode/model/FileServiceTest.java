package com.coffeecode.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;

class FileServiceTest {
    @Mock private IJsonService jsonService;
    private FileService fileService;
    @TempDir File tempDir;

    private static final String VALID_JSON = """
            {
                "vocabulary": [
                    {"english": "hello", "indonesian": "halo"},
                    {"english": "world", "indonesian": "dunia"}
                ]
            }
            """;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileService = new FileService("test-dict.json", 1024 * 1024, jsonService);
    }

    @Test
    void loadVocabularies_WithNullPath_ShouldThrowException() {
        var exception = assertThrows(
            DictionaryException.class,
            () -> fileService.loadVocabularies(null)
        );
        assertEquals(ErrorCode.FILE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void loadVocabularies_WithEmptyPath_ShouldThrowException() {
        var exception = assertThrows(
            DictionaryException.class,
            () -> fileService.loadVocabularies("  ")
        );
        assertEquals(ErrorCode.FILE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void loadVocabularies_WithNonexistentFile_ShouldThrowException() {
        var exception = assertThrows(
            DictionaryException.class,
            () -> fileService.loadVocabularies("nonexistent.json")
        );
        assertEquals(ErrorCode.FILE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void loadDefaultDictionary_WhenFileNotFound_ShouldThrowException() {
        var exception = assertThrows(
            DictionaryException.class,
            () -> fileService.loadDefaultDictionary()
        );
        assertEquals(ErrorCode.FILE_NOT_FOUND, exception.getErrorCode());
    }

    private File createTestFile(String filename, String content) throws IOException {
        File testFile = new File(tempDir, filename);
        Files.writeString(testFile.toPath(), content);
        return testFile;
    }
}