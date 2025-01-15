package com.coffeecode;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.model.Language;
import com.coffeecode.model.SearchResult;
import com.coffeecode.repository.DictionaryRepository;
import com.coffeecode.repository.JsonDictionaryRepository;
import com.coffeecode.services.BinarySearch;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    // ANSI colors
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) throws IOException {
        try {
            runDemonstration();
        } catch (DictionaryException e) {
            logger.error("Application error", e);
        }
    }

    private static void runDemonstration() throws DictionaryException, IOException {
        DictionaryRepository repository = new JsonDictionaryRepository();
        DictionaryViewModel viewModel = new DictionaryViewModel(repository);
        viewModel.loadDictionary();

        printSortedLists(viewModel);
        performSearchDemonstrations(viewModel);
    }

    private static void printSortedLists(DictionaryViewModel viewModel) {
        System.out.println(ANSI_BLUE + "\n=== Sorted Word Lists ===" + ANSI_RESET);
        System.out.println("English words: " + viewModel.getEnglishWords());
        System.out.println("Indonesian words: " + viewModel.getIndonesianWords());
    }

    private static void performSearchDemonstrations(DictionaryViewModel viewModel) {
        System.out.println(ANSI_BLUE + "\n=== Search Demonstrations ===" + ANSI_RESET);
        
        String[][] testCases = {
            {"cat", "English"},
            {"kucing", "Indonesian"},
            {"zebra", "English"},
            {"apel", "Indonesian"}
        };

        for (String[] testCase : testCases) {
            performSearch(viewModel, testCase[0], Language.valueOf(testCase[1].toUpperCase()));
            System.out.println();
        }
    }

    private static void performSearch(DictionaryViewModel viewModel, String word, Language language) {
        BinarySearch search = new BinarySearch(viewModel.getVocabularies(), language);
        SearchResult result = search.search(word);
        
        System.out.println(ANSI_YELLOW + "\nSearching " + language + " word: " + word + ANSI_RESET);
        result.displaySteps();
        
        if (result.isFound()) {
            System.out.println(ANSI_GREEN + "Found translation: " + result.getResult() + ANSI_RESET);
        } else {
            System.out.println("Word not found!");
        }
    }
}