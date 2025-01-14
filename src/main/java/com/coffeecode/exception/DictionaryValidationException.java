package com.coffeecode.exception;

public class DictionaryValidationException extends RuntimeException {
    public DictionaryValidationException(String message) {
        super(message);
    }

    public DictionaryValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DictionaryValidationException(Throwable cause) {
        super(cause);
    }

}
