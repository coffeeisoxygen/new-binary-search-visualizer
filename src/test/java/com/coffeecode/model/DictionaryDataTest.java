package com.coffeecode.model;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryDataTest {
    private DictionaryData dictionaryData;

    @BeforeEach
    void setUp() {
        dictionaryData = new DictionaryData();
    }

    @Test
    void testDictionaryContentInBothLanguages() {
        // Test English to Indonesian
        assertEquals(Language.ENGLISH, dictionaryData.getCurrentLanguage());
        Map<String, String> englishDict = dictionaryData.getCurrentDictionary();
        String englishWord = englishDict.keySet().iterator().next();
        String indonesianTranslation = dictionaryData.translate(englishWord);
        
        // Switch to Indonesian and test
        dictionaryData.switchLanguage();
        assertEquals(Language.INDONESIAN, dictionaryData.getCurrentLanguage());
        Map<String, String> indoDict = dictionaryData.getCurrentDictionary();
        
        // Verify the translation works both ways
        String backToEnglish = dictionaryData.translate(indonesianTranslation);
        assertEquals(englishWord.toLowerCase(), backToEnglish.toLowerCase());
        
        // Verify dictionaries are different
        assertFalse(englishDict.equals(indoDict));
        
        // Verify both dictionaries have content
        assertFalse(englishDict.isEmpty());
        assertFalse(indoDict.isEmpty());
        
        // Test a known English-Indonesian pair if available
        if (englishDict.containsKey("hello")) {
            assertEquals("halo", dictionaryData.translate("hello").toLowerCase());
            dictionaryData.switchLanguage();
            assertEquals("hello", dictionaryData.translate("halo").toLowerCase());
        }
    }

    @Test
    void testCaseSensitiveTranslation() {
        String mixedCaseWord = "HeLLo";
        String lowerCaseWord = "hello";
        String upperCaseWord = "HELLO";
        
        String translation1 = dictionaryData.translate(mixedCaseWord);
        String translation2 = dictionaryData.translate(lowerCaseWord);
        String translation3 = dictionaryData.translate(upperCaseWord);
        
        assertEquals(translation1, translation2);
        assertEquals(translation2, translation3);
    }

    @Test
    void testWhitespaceHandling() {
        String wordWithSpaces = "   hello   ";
        String wordWithTabs = "\thello\t";
        String wordWithNewlines = "\nhello\n";
        
        assertEquals("Translation not found", dictionaryData.translate(wordWithSpaces));
        assertEquals("Translation not found", dictionaryData.translate(wordWithTabs));
        assertEquals("Translation not found", dictionaryData.translate(wordWithNewlines));
    }

    @Test
    void testSpecialCharacters() {
        String wordWithSpecialChars = "hello!@#$%";
        String wordWithNumbers = "hello123";
        String wordWithUnicode = "hello™®";
        
        assertEquals("Translation not found", dictionaryData.translate(wordWithSpecialChars));
        assertEquals("Translation not found", dictionaryData.translate(wordWithNumbers));
        assertEquals("Translation not found", dictionaryData.translate(wordWithUnicode));
    }

    @Test
    void testLongAndShortText() {
        String veryLongWord = "a".repeat(1000);
        String singleChar = "a";
        
        assertEquals("Translation not found", dictionaryData.translate(veryLongWord));
        assertEquals("Translation not found", dictionaryData.translate(singleChar));
    }

    @Test
    void testConsecutiveTranslations() {
        Map<String, String> dictionary = dictionaryData.getCurrentDictionary();
        String word = dictionary.keySet().iterator().next();
        
        String translation1 = dictionaryData.translate(word);
        String translation2 = dictionaryData.translate(word);
        String translation3 = dictionaryData.translate(word);
        
        assertEquals(translation1, translation2);
        assertEquals(translation2, translation3);
    }
}
