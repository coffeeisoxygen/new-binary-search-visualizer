package com.coffeecode.integration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

    private static final String[][] BASE_WORDS = {
        {"apple", "apel"},
        {"banana", "pisang"},
        {"cherry", "ceri"},
        {"date", "kurma"},
        {"elderberry", "sambucus"}
    };

    @TempDir
    File tempDir;
    private Dictionary dictionary;
    private String firstWord;
    private String lastWord;
    private String middleWord;

    @BeforeEach
    void setUp() throws IOException {
        String json = generateLargeDictionary(DICTIONARY_SIZE);
        File testFile = new File(tempDir, "large-dictionary.json");
        Files.writeString(testFile.toPath(), json);
        
        // Track boundary words for tests
        firstWord = BASE_WORDS[0][0] + "a";           // "applea"
        lastWord = BASE_WORDS[4][0] + "zz";          // "elderberryzz"
        middleWord = BASE_WORDS[2][0] + "m";         // "cherrym"
        
        IFileService fileService = new FileService(testFile.getPath());
        SearchStrategy searchStrategy = new BinarySearch();
        dictionary = new Dictionary(searchStrategy, fileService);
        dictionary.loadDefaultDictionary();
        // Debug statement to verify lastWord is present in the dictionary
        boolean lastWordPresent = dictionary.getEnglishWords().contains(lastWord);
        System.out.println("Last word present in dictionary: " + lastWordPresent);
    }

    @Test
    void search_WithLargeDictionary_ShouldCompleteQuickly() {
        assertTimeout(Duration.ofMillis(SEARCH_TIMEOUT_MS), () -> {
            SearchResult result = dictionary.search("apple", Language.ENGLISH);
            assertTrue(result.found());
        });
    }

    @Test
    void load_LargeDictionary_ShouldLoadQuickly() {
        assertTimeout(Duration.ofMillis(LOAD_TIMEOUT_MS), () -> {
            dictionary.loadDefaultDictionary();
        });
    }

    @Test
    void search_FirstWord_ShouldBeFast() {
        assertTimeout(Duration.ofMillis(SEARCH_TIMEOUT_MS), () -> {
            SearchResult firstResult = dictionary.search(firstWord, Language.ENGLISH);
            assertTrue(firstResult.found());
        });
        assertTimeout(Duration.ofMillis(SEARCH_TIMEOUT_MS), () -> {
            SearchResult lastResult = dictionary.search(lastWord, Language.ENGLISH);
            assertTrue(lastResult.found());
        });
    }

    @Test
    void search_MiddleWord_ShouldBeFast() {
        assertTimeout(Duration.ofMillis(SEARCH_TIMEOUT_MS), () -> {
            SearchResult result = dictionary.search(middleWord, Language.ENGLISH);
            assertTrue(result.found());
        });
    }

    @Test
    void search_NonexistentWord_ShouldBeFast() {
        assertTimeout(Duration.ofMillis(SEARCH_TIMEOUT_MS), () -> {
            SearchResult result = dictionary.search("notfound", Language.ENGLISH);
            assertFalse(result.found());
        });
    }

    private String generateLargeDictionary(int size) {
        String entries = IntStream.range(0, size)
                .mapToObj(i -> {
                    String[] basePair = BASE_WORDS[i % BASE_WORDS.length];
                    String suffix = generateSuffix(i / BASE_WORDS.length);
                    String englishWord = basePair[0] + suffix;
                    String indonesianWord = basePair[1] + suffix;
                    // Debug statement to verify the presence of lastWord
                    if (englishWord.equals("elderberryzz")) {
                        System.out.println("Last word elderberryzz is included in the dictionary.");
                    }
                    return String.format(
                            """
                        {"english": "%s", "indonesian": "%s"}
                    """.trim(),
                            englishWord, indonesianWord
                    );
                })
                .sorted((entry1, entry2) -> {
                    String englishWord1 = entry1.split("\"")[3];
                    String englishWord2 = entry2.split("\"")[3];
                    return englishWord1.compareTo(englishWord2);
                }) // Ensure sorted order
                .collect(Collectors.joining(",\n"));

        return String.format("""
            {
                "vocabulary": [
                    %s
                ]
            }
            """, entries);
    }

    private String generateSuffix(int index) {
        if (index == 0) {
            return "";
        }
        StringBuilder suffix = new StringBuilder();
        while (index > 0) {
            suffix.insert(0, (char) ('a' + (--index % 26)));
            index /= 26;
        }
        return suffix.toString();
    }
}
