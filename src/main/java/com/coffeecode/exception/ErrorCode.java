package com.coffeecode.exception;

public enum ErrorCode {
    // File related errors
    FILE_NOT_FOUND("DICT001", "File Not Found"),
    INVALID_JSON("DICT002", "Invalid Dictionary Format"),
    FILE_TOO_LARGE("DICT003", "File Too Large"),
    PERMISSION_DENIED("DICT004", "Access Denied"),
    // Dictionary related errors
    DUPLICATE_ENTRY("DICT005", "Duplicate Word"),
    INVALID_WORD("DICT006", "Invalid Word"),
    DICTIONARY_NOT_LOADED("DICT007", "Dictionary Not Loaded"),
    // General errors
    UNKNOWN_ERROR("DICT999", "Unknown Error");

    private final String code;
    private final String defaultMessage;

    ErrorCode(String code, String defaultMessage) {
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
