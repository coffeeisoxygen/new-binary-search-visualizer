package com.coffeecode.exception;

public record ErrorResponse(
        ErrorCode code,
        String message,
        boolean isRecoverable) {

}
