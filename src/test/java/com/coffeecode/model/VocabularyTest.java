package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;

class VocabularyTest {

    @Test
    void create_WithValidWords_ShouldCreateVocabulary() {
        Vocabulary vocab = Vocabulary.create("hello", "halo");
        assertEquals("hello", vocab.english());
        assertEquals("halo", vocab.indonesian());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void create_WithInvalidEnglishWord_ShouldThrowException(String english) {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> Vocabulary.create(english, "valid")
        );
        assertEquals(ErrorCode.INVALID_WORD, exception.getErrorCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void create_WithInvalidIndonesianWord_ShouldThrowException(String indonesian) {
        DictionaryException exception = assertThrows(
                DictionaryException.class,
                () -> Vocabulary.create("valid", indonesian)
        );
        assertEquals(ErrorCode.INVALID_WORD, exception.getErrorCode());
    }

    @Test
    void equals_WithSameWords_ShouldBeEqual() {
        Vocabulary vocab1 = Vocabulary.create("cat", "kucing");
        Vocabulary vocab2 = Vocabulary.create("cat", "kucing");
        assertEquals(vocab1, vocab2);
    }

    @Test
    void equals_WithDifferentWords_ShouldNotBeEqual() {
        Vocabulary vocab1 = Vocabulary.create("cat", "kucing");
        Vocabulary vocab2 = Vocabulary.create("dog", "anjing");
        assertNotEquals(vocab1, vocab2);
    }
}
