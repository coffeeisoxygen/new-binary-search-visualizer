package com.coffeecode.viewmodel;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    void shouldLoadAndSortEnglishWords() throws IOException {
        // Given
        List<Vocabulary> testData = Arrays.asList(
                new Vocabulary("zebra", "zebra"),
                new Vocabulary("apple", "apel"));
        when(repository.loadVocabularies(anyString())).thenReturn(testData);

        // When
        viewModel.loadDictionary();
        List<String> result = viewModel.getEnglishWords();

        // Then
        assertEquals(Arrays.asList("apple", "zebra"), result);
    }

    @Test
    void shouldLoadAndSortIndonesianWords() throws IOException {
        // Given
        List<Vocabulary> testData = Arrays.asList(
                new Vocabulary("dog", "anjing"),
                new Vocabulary("cat", "kucing"));
        when(repository.loadVocabularies(anyString())).thenReturn(testData);

        // When
        viewModel.loadDictionary();
        List<String> result = viewModel.getIndonesianWords();

        // Then
        assertEquals(Arrays.asList("anjing", "kucing"), result);
    }

    @Test
    void shouldReturnEmptyVocabulariesList() throws IOException {
        when(repository.loadVocabularies(anyString())).thenReturn(Collections.emptyList());
        viewModel.loadDictionary();
        assertTrue(viewModel.getVocabularies().isEmpty());
    }
}
