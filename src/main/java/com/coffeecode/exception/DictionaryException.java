package com.coffeecode.exception;

public class DictionaryException extends RuntimeException {

    private final ErrorCode errorCode;

    public DictionaryException(ErrorCode errorCode, String message) {
        super(String.format("[%s] %s", errorCode.getCode(), message));
        this.errorCode = errorCode;
    }

    public DictionaryException(ErrorCode errorCode, String message, Throwable cause) {
        super(String.format("[%s] %s", errorCode.getCode(), message), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
