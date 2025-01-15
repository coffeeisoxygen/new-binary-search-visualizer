package com.coffeecode.exception;

public class DictionaryException extends LoggedException {

    public DictionaryException(String message) {
        super(message);
    }

    public DictionaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
