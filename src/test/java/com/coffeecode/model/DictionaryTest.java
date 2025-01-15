package com.coffeecode.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ExceptionMessages;
import com.coffeecode.search.SearchResult;
import com.coffeecode.search.SearchStrategy;

class DictionaryTest {

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
    void loadDefaultDictionary_ShouldLoadVocabularies() throws DictionaryException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();
        assertEquals(testVocabularies, dictionary.getVocabularies());
    }

    @Test
    void loadDictionary_WithNullPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> dictionary.loadVocabularies(null)
        );
        assertEquals(ExceptionMessages.ERR_FILE_PATH_EMPTY, exception.getMessage());
    }

    @Test
    void loadDictionary_WithEmptyPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> dictionary.loadVocabularies("")
        );
        assertEquals(ExceptionMessages.ERR_FILE_PATH_EMPTY, exception.getMessage());
    }

    @Test
    void loadDictionary_WithInvalidPath_ShouldThrowException() {
        when(fileService.loadVocabularies("invalid/path"))
                .thenThrow(new DictionaryException(ExceptionMessages.ERR_FILE_NOT_FOUND.formatted("invalid/path")));

        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> dictionary.loadVocabularies("invalid/path")
        );
        assertTrue(exception.getMessage().contains("File not found:"));
    }

    // Word List Tests
    @Test
    void getEnglishWords_ShouldReturnSortedList() throws DictionaryException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();
        assertEquals(Arrays.asList("cat", "dog"), dictionary.getEnglishWords());
    }

    @Test
    void getIndonesianWords_ShouldReturnSortedList() throws DictionaryException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();
        assertEquals(Arrays.asList("anjing", "kucing"), dictionary.getIndonesianWords());
    }

    @Test
    void getWords_WithoutLoadingDictionary_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> dictionary.getEnglishWords()
        );
        assertEquals(ExceptionMessages.ERR_DICT_NOT_LOADED, exception.getMessage());
    }

    // Search Tests
    @Test
    void search_ExistingWord_ShouldReturnResult() throws DictionaryException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();

        SearchResult expected = new SearchResult(true, "cat", INDONESIAN_CAT);
        when(searchStrategy.search("cat", testVocabularies, Language.ENGLISH))
                .thenReturn(expected);

        assertEquals(expected, dictionary.search("cat", Language.ENGLISH));
    }

    @Test
    void search_NonexistentWord_ShouldReturnNotFound() throws DictionaryException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();

        SearchResult expected = new SearchResult(false, "bird", "");
        when(searchStrategy.search("bird", testVocabularies, Language.ENGLISH))
                .thenReturn(expected);

        assertEquals(expected, dictionary.search("bird", Language.ENGLISH));
    }
}
