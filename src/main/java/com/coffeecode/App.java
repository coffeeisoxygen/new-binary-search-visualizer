package com.coffeecode;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.builder.DictionaryBuilder;
import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Language;
import com.coffeecode.services.search.result.SearchResult;
import com.coffeecode.services.visualization.SearchVisualization;
import com.coffeecode.services.visualization.observer.SearchObserver;
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
                System.out.println(ANSI_BLUE + "\n=== Dictionary Content ===" + ANSI_RESET);
                printWordLists(viewModel);
                
                System.out.println(ANSI_BLUE + "\n=== Search Demonstrations ===" + ANSI_RESET);
                runSearchDemonstrations(viewModel);
            }
        } catch (DictionaryException e) {
            logger.error("Dictionary error: {}", e.getMessage());
        }
    }

    private static void demonstrateSearch(DictionaryViewModel viewModel, String word, Language language) {
        // Create visualization for this search
        SearchObserver visualization = new SearchVisualization(word, language);
        
        // Configure search with visualization
        viewModel.configureSearch(visualization);
        
        // Perform search
        SearchResult result = viewModel.search(word, language);
        
        // Add spacing between searches
        System.out.println();
    }

    private static void runSearchDemonstrations(DictionaryViewModel viewModel) {
        // Basic search demo
        demonstrateSearch(viewModel, "cat", Language.ENGLISH);
        demonstrateSearch(viewModel, "kucing", Language.INDONESIAN);
        
        // Edge cases demo
        demonstrateSearch(viewModel, "ant", Language.ENGLISH);      // First word
        demonstrateSearch(viewModel, "zebra", Language.ENGLISH);    // Last word
        demonstrateSearch(viewModel, "moon", Language.ENGLISH);     // Not found
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
