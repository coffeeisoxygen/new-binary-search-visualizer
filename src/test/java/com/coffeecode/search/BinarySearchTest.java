package com.coffeecode.search;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BinarySearchTest {

    private BinarySearch binarySearch;
    private List<Vocabulary> data;

    @Mock
    private Language language;

    @Mock
    private Language oppositeLanguage;

    @Mock
    private Vocabulary vocabulary;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        binarySearch = new BinarySearch();
        data = new ArrayList<>();

        when(language.getOpposite()).thenReturn(oppositeLanguage);
    }

    @Test
    void searchShouldThrowExceptionWhenWordIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> binarySearch.search(null, data, language));
    }

    @Test
    void searchShouldThrowExceptionWhenWordIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> binarySearch.search("  ", data, language));
    }

    @Test
    void searchShouldReturnNotFoundForEmptyList() {
        SearchResult result = binarySearch.search("test", data, language);

        assertFalse(result.found());
        assertEquals("test", result.searchedWord());
        assertEquals("", result.translation());
    }

    @Test
    void searchShouldFindWordWhenExists() {
        data.add(vocabulary);
        when(language.getWord(vocabulary)).thenReturn("test");
        when(oppositeLanguage.getWord(vocabulary)).thenReturn("测试");

        SearchResult result = binarySearch.search("test", data, language);

        assertTrue(result.found());
        assertEquals("test", result.searchedWord());
        assertEquals("测试", result.translation());
    }

    @Test
    void searchShouldReturnNotFoundWhenWordDoesNotExist() {
        data.add(vocabulary);
        when(language.getWord(vocabulary)).thenReturn("other");

        SearchResult result = binarySearch.search("test", data, language);

        assertFalse(result.found());
        assertEquals("test", result.searchedWord());
        assertEquals("", result.translation());
    }

    @Test
    void searchShouldBeCaseInsensitive() {
        data.add(vocabulary);
        when(language.getWord(vocabulary)).thenReturn("TEST");
        when(oppositeLanguage.getWord(vocabulary)).thenReturn("测试");

        SearchResult result = binarySearch.search("test", data, language);

        assertTrue(result.found());
        assertEquals("TEST", result.searchedWord());
        assertEquals("测试", result.translation());
    }
}
