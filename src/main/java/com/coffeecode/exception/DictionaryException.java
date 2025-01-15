package com.coffeecode.exception;

public class DictionaryException extends LoggedException {

    private static final String PREFIX = "Dictionary Error: ";

    public DictionaryException(String message) {
        super(PREFIX + message);
    }

    public DictionaryException(String message, Throwable cause) {
        super(PREFIX + message, cause);
    }
}
