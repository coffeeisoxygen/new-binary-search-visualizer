package com.coffeecode.exception;

public class DictionaryException extends LoggedException {

    public DictionaryException(String message) {
        // Remove duplicate prefix - already in ExceptionMessages
        super(message != null ? message : ExceptionMessages.ERR_UNKNOWN);
    }

    public DictionaryException(String message, Throwable cause) {
        // Remove duplicate prefix - already in ExceptionMessages
        super(message != null ? message : ExceptionMessages.ERR_UNKNOWN, cause);
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
