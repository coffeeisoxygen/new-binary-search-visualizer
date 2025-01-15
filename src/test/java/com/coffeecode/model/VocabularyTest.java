package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class VocabularyTest {

    @Test
    void constructor_WithValidInputs_ShouldCreateVocabulary() {
        assertDoesNotThrow(() -> new Vocabulary("hello", "halo"));
    }

    @Test
    void constructor_WithNullEnglish_ShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Vocabulary(null, "halo"));
        assertEquals("English word cannot be empty", exception.getMessage());
    }

    @Test
    void constructor_WithNullIndonesian_ShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Vocabulary("hello", null));
        assertEquals("Indonesian word cannot be empty", exception.getMessage());
    }

    @Test
    void constructor_WithBlankEnglish_ShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Vocabulary("  ", "halo"));
        assertEquals("English word cannot be empty", exception.getMessage());
    }

    @Test
    void constructor_WithBlankIndonesian_ShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Vocabulary("hello", "  "));
        assertEquals("Indonesian word cannot be empty", exception.getMessage());
    }
}
