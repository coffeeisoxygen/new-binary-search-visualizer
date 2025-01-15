package com.coffeecode;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Language;
import com.coffeecode.search.SearchResult;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) throws DictionaryException {
        DictionaryManager manager = new DictionaryManager.Builder()
            .withDictionaryPath("src/main/resources/vocabulary.json")
            .withMaxFileSize(10 * 1024 * 1024)
            .build();

        DictionaryViewModel viewModel = manager.getViewModel();
        
        // Load dictionary
        try {
            viewModel.loadDictionary();
            logger.info("Dictionary loaded successfully");
        } catch (DictionaryException e) {
            logger.error("Failed to load dictionary: {}", e.getMessage());
            return;
        }

        // Print word lists
        System.out.println(ANSI_BLUE + "\n=== Dictionary Content ===" + ANSI_RESET);
        printWordLists(viewModel);

        // Simulation demos
        System.out.println(ANSI_BLUE + "\n=== Search Demonstrations ===" + ANSI_RESET);
        searchDemo(viewModel, "cat", Language.ENGLISH);
        searchDemo(viewModel, "kucing", Language.INDONESIAN);
        searchDemo(viewModel, "nonexistent", Language.ENGLISH);
    }

    private static void printWordLists(DictionaryViewModel viewModel) {
        System.out.println(ANSI_YELLOW + "\nEnglish Words:" + ANSI_RESET);
        List<String> englishWords = viewModel.getEnglishWords();
        for (int i = 0; i < englishWords.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, englishWords.get(i));
        }

        System.out.println(ANSI_YELLOW + "\nIndonesian Words:" + ANSI_RESET);
        List<String> indonesianWords = viewModel.getIndonesianWords();
        for (int i = 0; i < indonesianWords.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, indonesianWords.get(i));
        }
    }

    private static void searchDemo(DictionaryViewModel viewModel, String word, Language language) {
        try {
            SearchResult result = viewModel.search(word, language);
            printSearchResult(result, language);
        } catch (IllegalArgumentException e) {
            logger.error("Search error: {}", e.getMessage());
        }
    }

    private static void printSearchResult(SearchResult result, Language language) {
        String langStr = language == Language.ENGLISH ? "English → Indonesian" : "Indonesian → English";
        System.out.println(ANSI_BLUE + "\nSearching " + langStr + ANSI_RESET);
        System.out.println("Word: " + ANSI_YELLOW + result.word() + ANSI_RESET);

        if (result.found()) {
            System.out.println("Translation: " + ANSI_GREEN + result.translation() + ANSI_RESET);
        } else {
            System.out.println("Translation: " + ANSI_YELLOW + "Word not found" + ANSI_RESET);
        }
    }
}
