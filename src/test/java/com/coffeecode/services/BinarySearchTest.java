package com.coffeecode.services;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.Language;
import com.coffeecode.model.SearchResult;
import com.coffeecode.model.Vocabulary;

class BinarySearchTest {
    private static final List<Vocabulary> TEST_DATA = Arrays.asList(
        new Vocabulary("cat", "kucing"),
        new Vocabulary("dog", "anjing"),
        new Vocabulary("fish", "ikan")
    );

    @Test
    void shouldFindIndonesianTranslation() {
        BinarySearch search = new BinarySearch(TEST_DATA, Language.ENGLISH);
        SearchResult result = search.search("cat");
        assertTrue(result.isFound());
        assertEquals("kucing", result.getResult());
    }

    @Test
    void shouldFindEnglishTranslation() {
        BinarySearch search = new BinarySearch(TEST_DATA, Language.INDONESIAN);
        SearchResult result = search.search("kucing");
        assertTrue(result.isFound());
        assertEquals("cat", result.getResult());
    }

    @Test
    void shouldThrowExceptionForEmptyWord() {
        BinarySearch search = new BinarySearch(TEST_DATA, Language.ENGLISH);
        assertThrows(IllegalArgumentException.class, () -> search.search(""));
    }
}