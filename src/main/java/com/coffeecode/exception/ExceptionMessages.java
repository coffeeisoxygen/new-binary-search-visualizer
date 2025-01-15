package com.coffeecode.exception;

public final class ExceptionMessages {

    private ExceptionMessages() {
    } // Prevent instantiation

    // File errors
    public static final String ERR_FILE_PATH_EMPTY = "File path cannot be empty";
    public static final String ERR_FILE_NOT_FOUND = "File not found: %s";
    public static final String ERR_FILE_NOT_READABLE = "Cannot read file: %s";

    // JSON errors
    public static final String ERR_INVALID_JSON = "Invalid JSON structure: %s";
    public static final String ERR_PARSE_JSON = "Failed to parse dictionary file at %s";
    public static final String ERR_CONVERT_JSON = "Failed to convert JSON to vocabulary list";
}
