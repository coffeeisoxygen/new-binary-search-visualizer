package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class VocabularyTest {

    @Test
    void shouldCreateValidVocabulary() {
        Vocabulary vocab = new Vocabulary("hello", "halo");
        assertEquals("hello", vocab.english());
        assertEquals("halo", vocab.indonesian());
    }

    @Test
    void shouldThrowExceptionForNullEnglishWord() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vocabulary(null, "halo"));
    }

    @Test
    void shouldThrowExceptionForBlankEnglishWord() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vocabulary("  ", "halo"));
    }

    @Test
    void shouldThrowExceptionForNullIndonesianWord() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vocabulary("hello", null));
    }

    @Test
    void shouldThrowExceptionForBlankIndonesianWord() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vocabulary("hello", "  "));
    }
}
