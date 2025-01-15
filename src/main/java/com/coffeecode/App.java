package com.coffeecode;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.builder.DictionaryBuilder;
import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Language;
import com.coffeecode.services.visualization.SearchVisualization;
import com.coffeecode.services.visualization.formater.SearchStepFormatter;
import com.coffeecode.services.visualization.observer.SearchObserver;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final Scanner scanner = new Scanner(System.in);
    private static final SearchStepFormatter formatter = new SearchStepFormatter();
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = null;

    public static void main(String[] args) {
        try {
            DictionaryViewModel viewModel = new DictionaryBuilder()
                    .withDictionaryPath("src/main/resources/vocabulary.json")
                    .withMaxFileSize(10 * 1024 * 1024)
                    .build();

            if (viewModel.isLoaded()) {
                runInteractiveSearch(viewModel);
            }
        } catch (DictionaryException e) {
            logger.error("Dictionary error: {}", e.getMessage());
        }
    }

    private static void runInteractiveSearch(DictionaryViewModel viewModel) {
        // Display welcome screen
        System.out.println(formatter.formatWelcomeScreen(
                viewModel.getWordsByLanguage(Language.ENGLISH),
                viewModel.getWordsByLanguage(Language.INDONESIAN)
        ));

        while (true) {
            // Get search word
            System.out.print(formatter.formatInputPrompt());
            String word = getValidInput();
            if ("quit".equalsIgnoreCase(word)) {
                break;
            }

            // Get language choice
            System.out.println(formatter.formatLanguagePrompt());
            Language language = getValidLanguage();
            if (language == null) {
                break;
            }

            // Perform search with visualization
            demonstrateSearch(viewModel, word, language);
        }
    }

    private static String getValidInput() {
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if ("quit".equalsIgnoreCase(input)) {
                return input;
            }
            if (input.matches("[a-zA-Z\s-]+")) {
                return input;
            }
            System.out.println(ANSI_YELLOW + "Invalid input. Use only letters, spaces, and hyphens." + ANSI_RESET);
        }
    }

    private static Language getValidLanguage() {
        while (true) {
            String choice = scanner.nextLine().trim();
            if ("quit".equalsIgnoreCase(choice)) {
                return null;
            }
            if ("1".equals(choice)) {
                return Language.ENGLISH;
            }
            if ("2".equals(choice)) {
                return Language.INDONESIAN;
            }
            System.out.println(ANSI_YELLOW + "Invalid choice. Enter 1 for English or 2 for Indonesian." + ANSI_RESET);
        }
    }

    private static void demonstrateSearch(DictionaryViewModel viewModel, String word, Language language) {
        // Create visualization observer
        SearchObserver visualization = new SearchVisualization(word, language);

        // Configure view model with observer
        viewModel.configureSearch(visualization);

        // Perform search with visualization
        viewModel.search(word, language);

        // Add spacing for next operation
        System.out.println();
    }
}
