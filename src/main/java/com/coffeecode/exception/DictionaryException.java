package com.coffeecode.exception;

public class DictionaryException extends LoggedException {

    public DictionaryException(String message) {
        super(message != null ? message : "Unknown error");
    }

    public DictionaryException(String message, Throwable cause) {
        super(message != null ? message : "Unknown error", cause);
    }

    @Override
    public String toString() {
        String message = getMessage();
        Throwable cause = getCause();
        if (cause != null) {
            return message + ": " + cause.toString();
        }
        return message;
    }
}
