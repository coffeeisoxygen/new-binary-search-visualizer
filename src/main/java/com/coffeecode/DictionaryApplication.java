package com.coffeecode;

import javax.swing.SwingUtilities;

import com.coffeecode.repository.DictionaryRepository;
import com.coffeecode.repository.JsonDictionaryRepository;
import com.coffeecode.view.DictionaryFrame;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class DictionaryApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DictionaryRepository repository = new JsonDictionaryRepository();
                DictionaryViewModel viewModel = new DictionaryViewModel(repository);
                viewModel.loadDictionary();

                DictionaryFrame frame = new DictionaryFrame(viewModel);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}