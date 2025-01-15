package com.coffeecode.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.coffeecode.search.SearchResult;
import com.coffeecode.search.SearchStrategy;

public class DictionaryTest {

    private static final String INDONESIAN_CAT = "kucing";
    private static final String INDONESIAN_DOG = "anjing";

    @Mock
    private SearchStrategy searchStrategy;
    @Mock
    private IFileService fileService;
    private Dictionary dictionary;
    private List<Vocabulary> testVocabularies;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dictionary = new Dictionary(searchStrategy, fileService);
        testVocabularies = Arrays.asList(
                new Vocabulary("cat", INDONESIAN_CAT),
                new Vocabulary("dog", INDONESIAN_DOG)
        );
    }

    // Loading Tests
    @Test
    void loadDefaultDictionary_ShouldLoadVocabularies() throws IOException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();
        assertEquals(testVocabularies, dictionary.getVocabularies());
    }

    @Test
    void loadDictionaryWithInvalidPathShouldThrowException() throws IOException {
        // Setup mock behavior
        when(fileService.loadVocabularies("invalid/path"))
            .thenThrow(new IOException("File not found: invalid/path"));
        
        // Execute and verify
        IOException exception = assertThrows(
            IOException.class,
            () -> dictionary.loadVocabularies("invalid/path")
        );
        
        // Verify exact message
        assertEquals("File not found: invalid/path", exception.getMessage());
        
        // Verify mock was called
        verify(fileService).loadVocabularies("invalid/path");
    }

    @Test
    void loadDictionary_WithNullPath_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dictionary.loadVocabularies(null)
        );
        assertEquals("File path cannot be empty", exception.getMessage());
    }

    @Test
    void loadDictionary_WithEmptyPath_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dictionary.loadVocabularies("")
        );
        assertEquals("File path cannot be empty", exception.getMessage());
    }

    @Test
    void loadDictionary_WithInvalidPath_ShouldThrowException() throws IOException {
        when(fileService.loadVocabularies("invalid/path"))
                .thenThrow(new IOException("File not found: invalid/path"));

        IOException exception = assertThrows(
                IOException.class,
                () -> dictionary.loadVocabularies("invalid/path")
        );
        assertEquals("File not found: invalid/path", exception.getMessage());
    }

    @Test
    void loadDictionary_WithInvalidJson_ShouldThrowException() throws IOException {
        when(fileService.loadVocabularies("invalid.json"))
                .thenThrow(new IOException("Invalid file format: missing vocabulary array"));

        IOException exception = assertThrows(
                IOException.class,
                () -> dictionary.loadVocabularies("invalid.json")
        );
        assertEquals("Invalid file format: missing vocabulary array", exception.getMessage());
    }

    // Word List Tests  
    @Test
    void getEnglishWords_ShouldReturnSortedList() throws IOException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();
        assertEquals(Arrays.asList("cat", "dog"), dictionary.getEnglishWords());
    }

    @Test
    void getIndonesianWords_ShouldReturnSortedList() throws IOException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();
        assertEquals(Arrays.asList("anjing", "kucing"), dictionary.getIndonesianWords());
    }

    // Search Tests
    @Test
    void search_ExistingWord_ShouldReturnResult() throws IOException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();

        SearchResult expected = new SearchResult(true, "cat", INDONESIAN_CAT);
        when(searchStrategy.search("cat", testVocabularies, Language.ENGLISH))
                .thenReturn(expected);

        assertEquals(expected, dictionary.search("cat", Language.ENGLISH));
    }

    @Test
    void search_NonexistentWord_ShouldReturnNotFound() throws IOException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();

        SearchResult expected = new SearchResult(false, "bird", "");
        when(searchStrategy.search("bird", testVocabularies, Language.ENGLISH))
                .thenReturn(expected);

        assertEquals(expected, dictionary.search("bird", Language.ENGLISH));
    }
}
