package com.coffeecode.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
import com.coffeecode.services.file.FileService;
import com.coffeecode.services.json.IJsonService;

class FileServiceTest {

    private static final String TEST_RESOURCES = "src/test/resources/";
    private static final String TEST_VOCABULARY = TEST_RESOURCES + "test-vocabulary.json";
    private static final String EMPTY_VOCABULARY = TEST_RESOURCES + "empty-vocabulary.json";
    private static final String FULL_VOCABULARY = TEST_RESOURCES + "vocabulary.json";

    private IJsonService jsonService;
    private FileService fileService;
    private List<Vocabulary> testVocabularies;

    @BeforeEach
    void setUp() {
        jsonService = mock(IJsonService.class);
        fileService = new FileService(TEST_VOCABULARY, 1024 * 1024, jsonService);
        testVocabularies = Arrays.asList(
                new Vocabulary("cat", "kucing"),
                new Vocabulary("dog", "anjing")
        );
    }

    @Test
    void loadVocabularies_WithValidPath_ShouldReturnVocabularies() throws DictionaryException {
        when(jsonService.parseVocabularyFile(any())).thenReturn(testVocabularies);
        List<Vocabulary> result = fileService.loadVocabularies(TEST_VOCABULARY);

        assertEquals(2, result.size());
        verify(jsonService).parseVocabularyFile(any());
    }

    @Test
    void loadVocabularies_WithNullPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadVocabularies(null)
        );
        assertEquals(ErrorCode.FILE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void loadVocabularies_WithEmptyPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadVocabularies("  ")
        );
        assertEquals(ErrorCode.FILE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void loadVocabularies_WithNonexistentFile_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> fileService.loadVocabularies("nonexistent.json")
        );
        assertEquals(ErrorCode.FILE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void loadDefaultDictionary_ShouldLoadFromDefaultPath() throws DictionaryException {
        when(jsonService.parseVocabularyFile(any())).thenReturn(testVocabularies);
        List<Vocabulary> result = fileService.loadDefaultDictionary();

        assertNotNull(result);
        verify(jsonService).parseVocabularyFile(any());
    }
}
