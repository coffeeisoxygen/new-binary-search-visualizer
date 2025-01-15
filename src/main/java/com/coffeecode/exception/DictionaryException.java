package com.coffeecode.exception;

public class DictionaryException extends RuntimeException {
    private final ErrorCode errorCode;

    public DictionaryException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public DictionaryException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
