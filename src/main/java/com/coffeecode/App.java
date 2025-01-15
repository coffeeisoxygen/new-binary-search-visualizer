package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.model.SearchResult;
import com.coffeecode.repository.DictionaryRepository;
import com.coffeecode.repository.JsonDictionaryRepository;
import com.coffeecode.services.BinarySearch;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            DictionaryRepository repository = new JsonDictionaryRepository();
            DictionaryViewModel viewModel = new DictionaryViewModel(repository);
            viewModel.loadDictionary();

            // Display sorted lists
            System.out.println("=== Sorted Word Lists ===");
            System.out.println("English words: " + viewModel.getEnglishWords());
            System.out.println("Indonesian words: " + viewModel.getIndonesianWords());

            // Create binary search instances
            BinarySearch englishSearch = new BinarySearch(viewModel.getVocabularies(), Language.ENGLISH);
            BinarySearch indonesianSearch = new BinarySearch(viewModel.getVocabularies(), Language.INDONESIAN);

            // Search demonstrations
            System.out.println("\n=== Search Demonstrations ===");

            // Search English word
            String englishWord = "cat";
            SearchResult englishResult = englishSearch.search(englishWord);
            displaySearchResult("English", englishWord, englishResult);

            // Search Indonesian word
            String indonesianWord = "kucing";
            SearchResult indonesianResult = indonesianSearch.search(indonesianWord);
            displaySearchResult("Indonesian", indonesianWord, indonesianResult);

        } catch (Exception e) {
            logger.error("Application error", e);
        }
    }

    private static void displaySearchResult(String language, String word, SearchResult result) {
        System.out.println("\nSearching " + language + " word: " + word);
        result.displaySteps();
        
        if (result.isFound()) {
            System.out.println("Found translation: " + result.getResult());
        } else {
            System.out.println("Word not found!");
        }
    }
}