package com.coffeecode.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
import com.coffeecode.model.IDictionary;
import com.coffeecode.model.Language;
import com.coffeecode.search.SearchResult;

class DictionaryViewModelTest {
    @Mock private IDictionary dictionary;
    private DictionaryViewModel viewModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new DictionaryViewModel(dictionary);
    }

    @Test
    void loadDictionary_Success_ShouldSetLoadedFlag() {
        doNothing().when(dictionary).loadDefaultDictionary();
        viewModel.loadDictionary();
        assertTrue(viewModel.isDictionaryLoaded());
    }

    @Test
    void loadDictionary_Failure_ShouldHandleError() {
        doThrow(new DictionaryException(ErrorCode.FILE_NOT_FOUND, "Test error"))
            .when(dictionary).loadDefaultDictionary();
        
        viewModel.loadDictionary();
        assertFalse(viewModel.isDictionaryLoaded());
    }

    @Test
    void search_WithoutLoadingDictionary_ShouldThrowException() {
        var exception = assertThrows(
            DictionaryException.class,
            () -> viewModel.search("word", Language.ENGLISH)
        );
        assertEquals(ErrorCode.DICTIONARY_NOT_LOADED, exception.getErrorCode());
    }

    @Test
    void search_WithEmptyWord_ShouldThrowException() {
        doNothing().when(dictionary).loadDefaultDictionary();
        viewModel.loadDictionary();
        
        var exception = assertThrows(
            DictionaryException.class,
            () -> viewModel.search("", Language.ENGLISH)
        );
        assertEquals(ErrorCode.INVALID_WORD, exception.getErrorCode());
    }

    @Test
    void search_WithValidWord_ShouldReturnResult() {
        SearchResult expected = new SearchResult(true, "cat", "kucing");
        doNothing().when(dictionary).loadDefaultDictionary();
        when(dictionary.search("cat", Language.ENGLISH)).thenReturn(expected);
        
        viewModel.loadDictionary();
        SearchResult result = viewModel.search("cat", Language.ENGLISH);
        
        assertEquals(expected, result);
    }

    @Test
    void search_WithNonexistentWord_ShouldReturnNotFound() {
        SearchResult expected = new SearchResult(false, "xyz", "");
        doNothing().when(dictionary).loadDefaultDictionary();
        when(dictionary.search("xyz", Language.ENGLISH)).thenReturn(expected);
        
        viewModel.loadDictionary();
        SearchResult result = viewModel.search("xyz", Language.ENGLISH);
        
        assertFalse(result.found());
        assertEquals("xyz", result.word());
        assertEquals("", result.translation());
    }
}
