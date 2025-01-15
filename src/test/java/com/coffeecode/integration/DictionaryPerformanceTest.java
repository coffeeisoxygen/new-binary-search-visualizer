package com.coffeecode.integration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.coffeecode.model.Dictionary;
import com.coffeecode.model.FileService;
import com.coffeecode.model.IFileService;
import com.coffeecode.model.Language;
import com.coffeecode.search.BinarySearch;
import com.coffeecode.search.SearchResult;
import com.coffeecode.search.SearchStrategy;

class DictionaryPerformanceTest {
    private static final int DICTIONARY_SIZE = 1000;
    private static final long SEARCH_TIMEOUT_MS = 50;
    private static final long LOAD_TIMEOUT_MS = 200;
    
    @TempDir File tempDir;
    private Dictionary dictionary;

    @BeforeEach
    void setUp() throws IOException {
        String json = generateLargeDictionary(DICTIONARY_SIZE);
        File testFile = new File(tempDir, "large-dictionary.json");
        Files.writeString(testFile.toPath(), json);
        
        IFileService fileService = new FileService(testFile.getPath());
        SearchStrategy searchStrategy = new BinarySearch();
        dictionary = new Dictionary(searchStrategy, fileService);
        dictionary.loadDefaultDictionary();
    }

    @Test
    void search_WithLargeDictionary_ShouldCompleteQuickly() {
        assertTimeout(Duration.ofMillis(SEARCH_TIMEOUT_MS), () -> {
            SearchResult result = dictionary.search("word500", Language.ENGLISH);
            assertTrue(result.found());
        });
    }

    @Test
    void load_LargeDictionary_ShouldLoadQuickly() {
        assertTimeout(Duration.ofMillis(LOAD_TIMEOUT_MS), () -> {
            dictionary.loadDefaultDictionary();
        });
    }

    private String generateLargeDictionary(int size) {
        String entries = IntStream.range(0, size)
            .mapToObj(i -> String.format(
                """
                    {"english": "word%d", "indonesian": "kata%d"}
                """.trim(), i, i))
            .collect(Collectors.joining(",\n"));

        return String.format("""
            {
                "vocabulary": [
                    %s
                ]
            }
            """, entries);
    }
}