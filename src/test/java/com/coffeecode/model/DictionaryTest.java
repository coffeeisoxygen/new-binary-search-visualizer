package com.coffeecode.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
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

    @Test
    void loadDefaultDictionary_ShouldLoadVocabularies() throws DictionaryException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();
        assertEquals(testVocabularies, dictionary.getVocabularies());
    }

    @Test
    void loadVocabularies_WithNullPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> dictionary.loadVocabularies(null)
        );
        assertEquals(ErrorCode.INVALID_WORD, exception.getErrorCode());
    }

    @Test
    void loadVocabularies_WithEmptyPath_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> dictionary.loadVocabularies("  ")
        );
        assertEquals(ErrorCode.INVALID_WORD, exception.getErrorCode());
    }

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
    void search_WithValidWord_ShouldReturnResult() throws DictionaryException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();

        SearchResult expected = new SearchResult(true, "cat", INDONESIAN_CAT);
        when(searchStrategy.search("cat", testVocabularies, Language.ENGLISH))
                .thenReturn(expected);

        assertEquals(expected, dictionary.search("cat", Language.ENGLISH));
    }

    @Test
    void search_WithNullWord_ShouldThrowException() throws DictionaryException {
        when(fileService.loadDefaultDictionary()).thenReturn(testVocabularies);
        dictionary.loadDefaultDictionary();

        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> dictionary.search(null, Language.ENGLISH)
        );
        assertEquals(ErrorCode.INVALID_WORD, exception.getErrorCode());
    }

    @Test
    void search_WithoutLoadingDictionary_ShouldThrowException() {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> dictionary.search("cat", Language.ENGLISH)
        );
        assertEquals(ErrorCode.DICTIONARY_NOT_LOADED, exception.getErrorCode());
    }
}
