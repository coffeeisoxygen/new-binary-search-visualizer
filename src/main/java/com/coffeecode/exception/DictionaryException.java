package com.coffeecode.exception;

public class DictionaryException extends RuntimeException {
    public DictionaryException(String message) {
        super(message);
    }

    public DictionaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DictionaryException(Throwable cause) {
        super(cause);
    }

}
