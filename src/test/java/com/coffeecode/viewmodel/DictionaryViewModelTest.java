package com.coffeecode.viewmodel;

import java.io.IOException;

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

import com.coffeecode.model.IDictionary;
import com.coffeecode.model.Language;
import com.coffeecode.search.SearchResult;

class DictionaryViewModelTest {

    @Mock
    private IDictionary dictionary;

    private DictionaryViewModel viewModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new DictionaryViewModel(dictionary);
    }

    @Test
    void loadDictionary_Success_ShouldSetLoadedFlag() throws IOException {
        doNothing().when(dictionary).loadDefaultDictionary();
        viewModel.loadDictionary();
        assertTrue(viewModel.isDictionaryLoaded());
    }

    @Test
    void loadDictionary_Failure_ShouldThrowException() throws IOException {
        doThrow(new IOException("Test error")).when(dictionary).loadDefaultDictionary();
        assertThrows(IOException.class, () -> viewModel.loadDictionary());
        assertFalse(viewModel.isDictionaryLoaded());
    }

    @Test
    void search_WithoutLoading_ShouldThrowException() {
        assertThrows(IllegalStateException.class,
                () -> viewModel.search("word", Language.ENGLISH));
    }

    @Test
    void search_WithEmptyWord_ShouldThrowException() throws IOException {
        viewModel.loadDictionary();
        assertThrows(IllegalArgumentException.class,
                () -> viewModel.search("", Language.ENGLISH));
    }

    @Test
    void search_WithValidWord_ShouldReturnResult() throws IOException {
        SearchResult expectedResult = new SearchResult(true, "cat", "kucing");
        when(dictionary.search("cat", Language.ENGLISH)).thenReturn(expectedResult);

        viewModel.loadDictionary();
        SearchResult result = viewModel.search("cat", Language.ENGLISH);

        assertEquals(expectedResult, result);
    }
}
