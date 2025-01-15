package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LanguageTest {

    @Test
    void testGetWordEnglish() {
        Vocabulary vocabulary = mock(Vocabulary.class);
        when(vocabulary.english()).thenReturn("Hello");

        String word = Language.ENGLISH.getWord(vocabulary);
        assertEquals("Hello", word);
    }

    @Test
    void testGetWordIndonesian() {
        Vocabulary vocabulary = mock(Vocabulary.class);
        when(vocabulary.indonesian()).thenReturn("Halo");

        String word = Language.INDONESIAN.getWord(vocabulary);
        assertEquals("Halo", word);
    }

    @Test
    void testGetOppositeEnglish() {
        assertEquals(Language.INDONESIAN, Language.ENGLISH.getOpposite());
    }

    @Test
    void testGetOppositeIndonesian() {
        assertEquals(Language.ENGLISH, Language.INDONESIAN.getOpposite());
    }
}
