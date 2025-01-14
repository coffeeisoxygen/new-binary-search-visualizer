package com.coffeecode.exception;

public class DictionaryServiceException extends RuntimeException {
    public DictionaryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
