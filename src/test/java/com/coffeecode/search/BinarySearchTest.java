package com.coffeecode.search;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;

class BinarySearchTest {

    private BinarySearch search;
    private List<Vocabulary> dictionary;

    @BeforeEach
    void setUp() {
        search = new BinarySearch();
        dictionary = Arrays.asList(
                new Vocabulary("apple", "apel"),
                new Vocabulary("cat", "kucing"),
                new Vocabulary("dog", "anjing"),
                new Vocabulary("zebra", "zebra")
        );
    }

    @Test
    void search_WithExistingEnglishWord_ShouldReturnFound() {
        SearchResult result = search.search("cat", dictionary, Language.ENGLISH);

        assertTrue(result.found());
        assertEquals("cat", result.word());
        assertEquals("kucing", result.translation());
    }

    @Test
    void search_WithNonexistentWord_ShouldReturnNotFound() {
        SearchResult result = search.search("bird", dictionary, Language.ENGLISH);

        assertFalse(result.found());
        assertEquals("bird", result.word());
        assertEquals("", result.translation());
    }

    @Test
    void search_WithNullWord_ShouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> search.search(null, dictionary, Language.ENGLISH)
        );
    }

    @Test
    void search_WithEmptyDictionary_ShouldReturnNotFound() {
        SearchResult result = search.search("cat", List.of(), Language.ENGLISH);

        assertFalse(result.found());
        assertEquals("cat", result.word());
        assertEquals("", result.translation());
    }
}
