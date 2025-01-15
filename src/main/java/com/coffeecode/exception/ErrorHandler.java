package com.coffeecode.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    private ErrorHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static ErrorResponse handle(Exception e) {
        if (e instanceof DictionaryException de) {
            return handleDictionaryException(de);
        }
        return handleUnexpectedException(e);
    }

    private static ErrorResponse handleDictionaryException(DictionaryException de) {
        ErrorCode code = de.getErrorCode();
        String message = String.format("[%s] %s: %s",
                code.getCode(),
                code.getDefaultMessage(),
                de.getMessage()
        );

        logger.error(message, de);

        // Determine if error is recoverable
        boolean isRecoverable = switch (code) {
            case FILE_NOT_FOUND, FILE_TOO_LARGE, PERMISSION_DENIED ->
                true;
            case INVALID_JSON, DUPLICATE_ENTRY ->
                false;
            default ->
                false;
        };

        return new ErrorResponse(code, message, isRecoverable);
    }

    private static ErrorResponse handleUnexpectedException(Exception e) {
        String message = String.format("[%s] Unexpected error: %s",
                ErrorCode.UNKNOWN_ERROR.getCode(),
                e.getMessage()
        );

        logger.error(message, e);

        return new ErrorResponse(
                ErrorCode.UNKNOWN_ERROR,
                message,
                false
        );
    }
}
