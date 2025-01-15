package com.coffeecode.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class SearchResultTest {

    @Test
    void create_WithValidValues_ShouldCreateResult() {
        SearchResult result = new SearchResult(true, "hello", "halo");

        assertTrue(result.found());
        assertEquals("hello", result.word());
        assertEquals("halo", result.translation());
    }

    @Test
    void create_WithNullValues_ShouldUseEmptyStrings() {
        SearchResult result = new SearchResult(false, null, null);

        assertFalse(result.found());
        assertEquals("", result.word());
        assertEquals("", result.translation());
    }
}
