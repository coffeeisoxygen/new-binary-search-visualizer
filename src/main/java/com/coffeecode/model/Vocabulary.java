package com.coffeecode.model;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;

/*
 * this class is responsible for Data Structure from json file
 */
public record Vocabulary(String english, String indonesian) {

    public Vocabulary  {
        if (english == null || english.isBlank()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_WORD,
                    "English word cannot be empty"
            );
        }
        if (indonesian == null || indonesian.isBlank()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_WORD,
                    "Indonesian word cannot be empty"
            );
        }
    }
}
