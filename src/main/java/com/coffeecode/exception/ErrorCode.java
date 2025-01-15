package com.coffeecode.exception;

public enum ErrorCode {
    // Path related errors
    INVALID_FILE_PATH("DICT000", "Invalid File Path"),
    // File related errors
    FILE_NOT_FOUND("DICT001", "File Not Found"),
    INVALID_JSON("DICT002", "Invalid Dictionary Format"),
    FILE_TOO_LARGE("DICT003", "File Too Large"),
    PERMISSION_DENIED("DICT004", "Access Denied"),
    INVALID_FILE_TYPE("DICT005", "Invalid File Type"),
    // Dictionary related errors
    DUPLICATE_ENTRY("DICT005", "Duplicate Word"),
    INVALID_WORD("DICT006", "Invalid Word"),
    DICTIONARY_NOT_LOADED("DICT007", "Dictionary Not Loaded"),
    // process related errors
    INVALID_DICTIONARY("DICT008", "Invalid Dictionary"),
    INVALID_CONFIGURATION("DICT009", "Invalid Configuration"),
    INITIALIZATION_ERROR("DICT010", "Initialization Error"),
    // Search related errors
    WORD_NOT_FOUND("DICT010", "Word Not Found"),
    // Sort related errors
    INVALID_SORT("DICT011", "Invalid Sort"),
    // General errors
    UNKNOWN_ERROR("DICT999", "Unknown Error");

    private final String code;
    private final String defaultMessage;

    ErrorCode(String code, String defaultMessage
    ) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
