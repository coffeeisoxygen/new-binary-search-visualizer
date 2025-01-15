package com.coffeecode.view;

import java.io.IOException;

import com.coffeecode.viewmodel.DictionaryViewModel;

public class DictionaryView {
    private final DictionaryViewModel viewModel;

    public DictionaryView(DictionaryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void display() {
        try {
            viewModel.loadDictionary("src/main/resources/vocabulary.json");
            System.out.println("English List: " + viewModel.getEnglishWords());
            System.out.println("Indonesian List: " + viewModel.getIndonesianWords());
        } catch (IOException e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
    }
}
