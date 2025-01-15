package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.repository.DictionaryRepository;
import com.coffeecode.repository.JsonDictionaryRepository;
import com.coffeecode.view.DictionaryView;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        DictionaryRepository repository = new JsonDictionaryRepository();
        DictionaryViewModel viewModel = new DictionaryViewModel(repository);
        DictionaryView view = new DictionaryView(viewModel);

        try {
            view.display();
        } catch (Exception e) {
            logger.error("Application error", e);
        }
    }
}