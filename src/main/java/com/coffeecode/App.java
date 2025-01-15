package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.builder.DictionaryBuilder;
import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Language;
import com.coffeecode.services.visualization.observer.SearchObserver;
import com.coffeecode.services.visualization.terminal.TerminalFormatter;
import com.coffeecode.services.visualization.terminal.TerminalHandler;
import com.coffeecode.services.visualization.terminal.TerminalVisualizer;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final TerminalHandler terminal;

    static {
        TerminalFormatter formatter = new TerminalFormatter();
        terminal = new TerminalHandler(formatter);
    }

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
        terminal.displayWelcomeScreen(
                viewModel.getWordsByLanguage(Language.ENGLISH),
                viewModel.getWordsByLanguage(Language.INDONESIAN)
        );

        boolean continueSearch = true;
        while (continueSearch) {
            String word = terminal.getSearchWord();
            Language language = terminal.getSearchLanguage();

            if ("quit".equalsIgnoreCase(word) || language == null) {
                continueSearch = false;
            } else {
                performSearch(viewModel, word, language);
            }
        }
    }

    private static void performSearch(DictionaryViewModel viewModel, String word, Language language) {
        try {
            SearchObserver visualizer = new TerminalVisualizer(word, language);
            viewModel.configureSearch(visualizer);
            viewModel.search(word, language);
        } catch (DictionaryException e) {
            logger.error("Search failed: {}", e.getMessage());
        }
    }
}
