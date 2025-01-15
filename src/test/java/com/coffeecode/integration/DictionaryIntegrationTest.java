package com.coffeecode.integration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.coffeecode.model.Dictionary;
import com.coffeecode.model.FileService;
import com.coffeecode.model.IFileService;
import com.coffeecode.model.IJsonService;
import com.coffeecode.model.JsonService;
import com.coffeecode.model.Language;
import com.coffeecode.search.BinarySearch;
import com.coffeecode.search.SearchResult;
import com.coffeecode.search.SearchStrategy;

class DictionaryIntegrationTest {
    private static final String TEST_RESOURCES = "src/test/resources/";
    private Dictionary dictionary;
    @TempDir File tempDir;

    private static final String VALID_JSON = """
            {
                "vocabulary": [
                    {"english": "cat", "indonesian": "kucing"},
                    {"english": "dog", "indonesian": "anjing"},
                    {"english": "fish", "indonesian": "ikan"}
                ]
            }
            """;

    @BeforeEach
    void setUp() throws IOException {
        // Create test file in target/test-classes
        File testDir = new File("target/test-classes");
        testDir.mkdirs();
        File testFile = new File(testDir, "test-dictionary.json");
        Files.writeString(testFile.toPath(), VALID_JSON);
        
        IFileService fileService = new FileService(testFile.getPath());
        IJsonService jsonService = new JsonService();
        SearchStrategy searchStrategy = new BinarySearch();
        dictionary = new Dictionary(searchStrategy, fileService);
    }

    @Test
    void loadAndSearch_WithValidDictionary_ShouldFindWord() {
        dictionary.loadDefaultDictionary();
        
        SearchResult result = dictionary.search("cat", Language.ENGLISH);
        
        assertTrue(result.found());
        assertEquals("cat", result.word());
        assertEquals("kucing", result.translation());
    }

    @Test
    void loadAndSearch_WithNonexistentWord_ShouldReturnNotFound() {
        dictionary.loadDefaultDictionary();
        
        SearchResult result = dictionary.search("xyz", Language.ENGLISH);
        
        assertFalse(result.found());
        assertEquals("xyz", result.word());
        assertTrue(result.translation().isEmpty());
    }

    @Test
    void searchInBothLanguages_ShouldFindWords() {
        dictionary.loadDefaultDictionary();
        
        SearchResult englishResult = dictionary.search("cat", Language.ENGLISH);
        SearchResult indonesianResult = dictionary.search("kucing", Language.INDONESIAN);
        
        assertTrue(englishResult.found());
        assertTrue(indonesianResult.found());
        assertEquals("kucing", englishResult.translation());
        assertEquals("cat", indonesianResult.translation());
    }

    private File createTestFile(String filename, String content) throws IOException {
        File testFile = new File(tempDir, filename);
        Files.writeString(testFile.toPath(), content);
        return testFile;
    }
}