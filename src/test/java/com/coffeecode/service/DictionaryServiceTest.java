package com.coffeecode.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.Language;

class DictionaryServiceTest {
    private DictionaryService service;

    @BeforeEach
    void setUp() {
        service = new DictionaryService();
    }

    @Test
    void translateShouldReturnTranslationForValidWord() {
        Optional<String> translation = service.translate("hello");
        assertTrue(translation.isPresent());
        assertEquals("halo", translation.get());
    }

    @Test
    void translateShouldReturnEmptyForInvalidWord() {
        Optional<String> translation = service.translate("invalidword");
        assertFalse(translation.isPresent());
    }

    @Test
    void cacheShouldWorkForRepeatedTranslations() {
        String word = "hello";
        Optional<String> first = service.translate(word);
        Optional<String> second = service.translate(word);
        
        assertTrue(first.isPresent());
        assertEquals(first.get(), second.get());
    }

    @Test
    void switchLanguageShouldChangeCurrentLanguage() {
        Language initial = service.getCurrentLanguage();
        Language switched = service.switchLanguage();
        
        assertNotEquals(initial, switched);
    }
}