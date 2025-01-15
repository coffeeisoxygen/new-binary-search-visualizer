package com.coffeecode;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.builder.DictionaryBuilder;
import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Language;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {
        try {
            DictionaryViewModel viewModel = new DictionaryBuilder()
                    .withDictionaryPath("src/main/resources/vocabulary.json")
                    .withMaxFileSize(10 * 1024 * 1024)
                    .build();

            if (viewModel.isLoaded()) {
                demonstrateDictionary(viewModel);
            }
        } catch (DictionaryException e) {
            logger.error("Dictionary error: {}", e.getMessage());
        }
    }

    private static void demonstrateDictionary(DictionaryViewModel viewModel) {
        // Print dictionary content
        System.out.println(ANSI_BLUE + "\n=== Dictionary Content ===" + ANSI_RESET);
        printWordLists(viewModel);

        // Demonstrate search
        System.out.println(ANSI_BLUE + "\n=== Search Demo ===" + ANSI_RESET);
        demonstrateSearch(viewModel, "cat", Language.ENGLISH);
        demonstrateSearch(viewModel, "kucing", Language.INDONESIAN);
    }

    private static void demonstrateSearch(DictionaryViewModel viewModel,
            String word, Language language) {
        SearchResult result = viewModel.search(word, language);
        System.out.printf("%sSearching for '%s' in %s: %s%s%n",
                ANSI_YELLOW, word, language,
                result.found() ? "Found: " + result.translation() : "Not found",
                ANSI_RESET);
    }

    private static void printWordLists(DictionaryViewModel viewModel) {
        System.out.println(ANSI_GREEN + "English words: " + ANSI_RESET);
        printWordList(viewModel.getWordsByLanguage(Language.ENGLISH));

        System.out.println(ANSI_GREEN + "\nIndonesian words: " + ANSI_RESET);
        printWordList(viewModel.getWordsByLanguage(Language.INDONESIAN));
    }

    private static void printWordList(List<String> words) {
        int columns = 5;
        int counter = 0;

        for (String word : words) {
            System.out.printf("%-15s", word);
            counter++;
            if (counter % columns == 0) {
                System.out.println();
            }
        }
        if (counter % columns != 0) {
            System.out.println();
        }
    }
}
