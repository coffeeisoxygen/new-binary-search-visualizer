package com.coffeecode.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;

class JsonServiceTest {

    private JsonService jsonService;
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

    private static final String EMPTY_ARRAY_JSON = """
            {
                "vocabulary": []
            }
            """;

    private static final String INVALID_STRUCTURE_JSON = """
            {
                "vocabulary": "not-an-array"
            }
            """;

    private static final String DUPLICATE_ENTRY_JSON = """
            {
                "vocabulary": [
                    {"english": "cat", "indonesian": "kucing"},
                    {"english": "cat", "indonesian": "meow"}
                ]
            }
            """;

    @BeforeEach
    void setUp() {
        jsonService = new JsonService();
    }

    @Test
    void parseVocabularyFile_WithValidJson_ShouldReturnVocabularyList() throws IOException {
        File testFile = createTestFile("valid.json", VALID_JSON);
        List<Vocabulary> result = jsonService.parseVocabularyFile(testFile);

        assertEquals(2, result.size());
        assertEquals("cat", result.get(0).english());
        assertEquals("kucing", result.get(0).indonesian());
    }

    @Test
    void parseVocabularyFile_WithEmptyArray_ShouldThrowException() throws IOException {
        File testFile = createTestFile("empty.json", EMPTY_ARRAY_JSON);

        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> jsonService.parseVocabularyFile(testFile)
        );
        assertEquals(ErrorCode.INVALID_JSON, exception.getErrorCode());
    }

    @Test
    void parseVocabularyFile_WithInvalidStructure_ShouldThrowException() throws IOException {
        File testFile = createTestFile("invalid.json", INVALID_STRUCTURE_JSON);

        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> jsonService.parseVocabularyFile(testFile)
        );
        assertEquals(ErrorCode.INVALID_JSON, exception.getErrorCode());
    }

    @Test
    void parseVocabularyFile_WithDuplicateEntries_ShouldThrowException() throws IOException {
        File testFile = createTestFile("duplicate.json", DUPLICATE_ENTRY_JSON);

        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> jsonService.parseVocabularyFile(testFile)
        );
        assertEquals(ErrorCode.DUPLICATE_ENTRY, exception.getErrorCode());
    }

    private File createTestFile(String filename, String content) throws IOException {
        File testFile = new File(tempDir, filename);
        Files.writeString(testFile.toPath(), content);
        return testFile;
    }
}
