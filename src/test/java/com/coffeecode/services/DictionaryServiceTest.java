package com.coffeecode.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.model.Vocabulary;
import com.coffeecode.repository.DictionaryRepository;

@ExtendWith(MockitoExtension.class)
class DictionaryServiceTest {
    @Mock
    private DictionaryRepository repository;

    @InjectMocks
    private DictionaryService service;

    @Test
    void shouldReturnSortedEnglishWords() throws IOException {
        // Given
        List<Vocabulary> testData = Arrays.asList(
                new Vocabulary("zebra", "zebra"),
                new Vocabulary("apple", "apel"));
        when(repository.loadVocabularies(anyString())).thenReturn(testData);

        // When
        service.loadDefaultDictionary();
        List<String> result = service.getEnglishWords();

        // Then
        assertEquals(Arrays.asList("apple", "zebra"), result);
    }

    @Test
    void shouldReturnSortedIndonesianWords() throws IOException {
        // Given
        List<Vocabulary> testData = Arrays.asList(
                new Vocabulary("zebra", "zebra"),
                new Vocabulary("apple", "apel"));
        when(repository.loadVocabularies(anyString())).thenReturn(testData);

        // When
        service.loadDefaultDictionary();
        List<String> result = service.getIndonesianWords();

        // Then
        assertEquals(Arrays.asList("apel", "zebra"), result);
    }
}
