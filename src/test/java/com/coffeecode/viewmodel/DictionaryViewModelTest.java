package com.coffeecode.viewmodel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.model.Vocabulary;
import com.coffeecode.repository.DictionaryRepository;

@ExtendWith(MockitoExtension.class)
class DictionaryViewModelTest {
    @Mock
    private DictionaryRepository repository;

    private DictionaryViewModel viewModel;

    @BeforeEach
    void setup() {
        viewModel = new DictionaryViewModel(repository);
    }

    @Test
    void shouldLoadDictionarySuccessfully() throws IOException {
        // Given
        List<Vocabulary> testData = Arrays.asList(
                new Vocabulary("hello", "halo"),
                new Vocabulary("world", "dunia"));
        when(repository.loadVocabularies(anyString())).thenReturn(testData);

        // When
        viewModel.loadDictionary();

        // Then
        List<String> englishWords = viewModel.getEnglishWords();
        assertEquals(Arrays.asList("hello", "world"), englishWords);
    }

    @Test
    void shouldPropagateIOException() {
        // Given
        try {
            when(repository.loadVocabularies(anyString()))
                    .thenThrow(new IOException("Test error"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Then
        assertThrows(IOException.class, () -> viewModel.loadDictionary());
    }
}
