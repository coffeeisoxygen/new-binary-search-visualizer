package com.coffeecode.model;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryDataTest {
    private DictionaryData dictionaryData;

    @BeforeEach
    void setUp() {
        dictionaryData = new DictionaryData();
    }

    @Test 
    void testInitialLanguageIsEnglish() {
        assertEquals(Language.ENGLISH, dictionaryData.getCurrentLanguage());
    }

    @Test
    void testSwitchLanguage() {
        dictionaryData.switchLanguage();
        assertEquals(Language.INDONESIAN, dictionaryData.getCurrentLanguage());
        dictionaryData.switchLanguage(); 
        assertEquals(Language.ENGLISH, dictionaryData.getCurrentLanguage());
    }

    @Test
    void testGetWordList() {
        List<String> wordList = dictionaryData.getWordList();
        assertNotNull(wordList);
        assertFalse(wordList.isEmpty());
    }

    @Test
    void testTranslateExistingWord() {
        Map<String, String> dictionary = dictionaryData.getCurrentDictionary();
        String word = dictionary.keySet().iterator().next();
        String translation = dictionaryData.translate(word);
        assertEquals(dictionary.get(word), translation);
    }

    @Test
    void testTranslateNonExistingWord() {
        String translation = dictionaryData.translate("nonexistentword");
        assertEquals("Translation not found", translation);
    }

    @Test
    void testGetCurrentDictionary() {
        Map<String, String> dictionary = dictionaryData.getCurrentDictionary();
        assertNotNull(dictionary);
        assertFalse(dictionary.isEmpty());
    }

    @Test
    void testToString() {
        String result = dictionaryData.toString();
        assertTrue(result.contains("currentDictionary"));
        assertTrue(result.contains("currentLanguage"));
    }

    @Test
    void testTranslateEmptyString() {
        String translation = dictionaryData.translate("");
        assertEquals("Translation not found", translation);
    }

    @Test
    void testTranslateNullWord() {
        String translation = dictionaryData.translate(null);
        assertEquals("Translation not found", translation);
    }

    @Test
    void testMultipleLanguageSwitches() {
        assertEquals(Language.ENGLISH, dictionaryData.getCurrentLanguage());
        dictionaryData.switchLanguage();
        assertEquals(Language.INDONESIAN, dictionaryData.getCurrentLanguage());
        dictionaryData.switchLanguage();
        assertEquals(Language.ENGLISH, dictionaryData.getCurrentLanguage());
        dictionaryData.switchLanguage();
        assertEquals(Language.INDONESIAN, dictionaryData.getCurrentLanguage());
    }

    @Test
    void testWordListConsistency() {
        List<String> initialList = dictionaryData.getWordList();
        dictionaryData.switchLanguage();
        List<String> afterSwitchList = dictionaryData.getWordList();
        assertNotNull(initialList);
        assertNotNull(afterSwitchList);
        assertFalse(initialList.equals(afterSwitchList));
    }
}