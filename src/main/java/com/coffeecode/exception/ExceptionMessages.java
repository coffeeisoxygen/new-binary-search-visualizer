package com.coffeecode.exception;

public final class ExceptionMessages {

    private ExceptionMessages() {
    } // Prevent instantiation

    public static final String PREFIX = "Dictionary Error: ";

    // File errors
    public static final String ERR_FILE_PATH_EMPTY = PREFIX + "File path cannot be empty";
    public static final String ERR_DEFAULT_DICT_NOT_FOUND = PREFIX + "Default dictionary not found at: %s";
    public static final String ERR_FILE_NOT_FOUND = PREFIX + "File not found at: %s";
    public static final String ERR_FILE_NOT_READABLE = PREFIX + "Cannot read file: %s";

    // JSON errors
    public static final String ERR_INVALID_JSON = PREFIX + "Invalid JSON structure: %s";
    public static final String ERR_PARSE_JSON = PREFIX + "Failed to parse dictionary file at %s";
    public static final String ERR_CONVERT_JSON = PREFIX + "Failed to convert JSON to vocabulary list";

    public static final String ERR_WORD_EMPTY = PREFIX + "Search word cannot be empty";
    public static final String ERR_DICT_NOT_LOADED = PREFIX + "Dictionary not loaded. Call loadDictionary() first";

    public static final String ERR_UNKNOWN = PREFIX + "Unknown error occurred";
}
