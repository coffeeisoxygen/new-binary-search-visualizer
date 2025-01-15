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

        // Technical message for logs
        String techMessage = String.format("[%s] %s: %s",
                code.getCode(),
                code.getDefaultMessage(),
                de.getMessage()
        );
        logger.error(techMessage, de);

        // User-friendly message and action
        String userMessage = getUserMessage(code);
        String action = getRequiredAction(code);

        return new ErrorResponse(
                code,
                techMessage,
                isRecoverable(code),
                userMessage,
                action
        );
    }

    private static String getUserMessage(ErrorCode code) {
        return switch (code) {
            case FILE_NOT_FOUND ->
                "Dictionary file could not be found";
            case FILE_TOO_LARGE ->
                "Dictionary file is too large to process";
            case PERMISSION_DENIED ->
                "Cannot access dictionary file";
            case INVALID_JSON ->
                "Dictionary file is corrupted";
            case DICTIONARY_NOT_LOADED ->
                "Dictionary needs to be loaded first";
            default ->
                "An error occurred while processing the dictionary";
        };
    }

    private static String getRequiredAction(ErrorCode code) {
        return switch (code) {
            case FILE_NOT_FOUND ->
                "Please select a valid dictionary file";
            case FILE_TOO_LARGE ->
                "Please use a smaller dictionary file (max 10MB)";
            case PERMISSION_DENIED ->
                "Please check file permissions";
            case DICTIONARY_NOT_LOADED ->
                "Please load a dictionary first";
            default ->
                "";
        };
    }

    private static boolean isRecoverable(ErrorCode code) {
        return switch (code) {
            case FILE_NOT_FOUND, FILE_TOO_LARGE, PERMISSION_DENIED, DICTIONARY_NOT_LOADED ->
                true;
            default ->
                false;
        };
    }

    private static ErrorResponse handleUnexpectedException(Exception e) {
        String techMessage = String.format("[%s] Unexpected error: %s",
                ErrorCode.UNKNOWN_ERROR.getCode(),
                e.getMessage()
        );
        logger.error(techMessage, e);

        return new ErrorResponse(
                ErrorCode.UNKNOWN_ERROR,
                techMessage,
                false,
                "An unexpected error occurred",
                "Please try again or contact support"
        );
    }
}
