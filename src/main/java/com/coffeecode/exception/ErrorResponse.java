package com.coffeecode.exception;

public record ErrorResponse(
        ErrorCode code,
        String message,
        boolean isRecoverable,
        String userMessage,
        String actionRequired) {

    public boolean requiresUserAction() {
        return actionRequired != null && !actionRequired.isEmpty();
    }
}
