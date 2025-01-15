package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class LanguageTest {

    private static final String ENGLISH_WORD = "cat";
    private static final String INDONESIAN_WORD = "kucing";
    private final Vocabulary vocabulary = new Vocabulary(ENGLISH_WORD, INDONESIAN_WORD);

    @Test
    void testGetWordEnglish() {
        assertEquals(ENGLISH_WORD, Language.ENGLISH.getWord(vocabulary));
    }

    @Test
    void testGetWordIndonesian() {
        assertEquals(INDONESIAN_WORD, Language.INDONESIAN.getWord(vocabulary));
    }

    @Test
    void testOppositeEnglish() {
        assertEquals(Language.INDONESIAN, Language.ENGLISH.getOpposite());
    }

    @Test
    void testOppositeIndonesian() {
        assertEquals(Language.ENGLISH, Language.INDONESIAN.getOpposite());
    }
}
