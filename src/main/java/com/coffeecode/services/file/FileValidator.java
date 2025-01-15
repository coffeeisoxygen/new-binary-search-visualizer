package com.coffeecode.services.file;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;

public class FileValidator {

    private static final Logger logger = LoggerFactory.getLogger(FileValidator.class);
    private final long maxFileSize;

    public FileValidator(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public File validateAndGetFile(String filePath) {
        logger.debug("Validating file: {}", filePath);
        File file = new File(filePath);
        validateFilePath(filePath);
        validateFileExists(file);
        validateFileSize(file);
        validateFileReadable(file);
        validateFileExtension(file);
        validatePathSecurity(file);
        logger.debug("File validation successful: {}", filePath);
        return file;
    }

    private void validateFilePath(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new DictionaryException(
                    ErrorCode.INVALID_FILE_PATH,
                    "File path cannot be null or empty"
            );
        }
    }

    private void validateFileExists(File file) {
        if (!file.exists()) {
            throw new DictionaryException(
                    ErrorCode.FILE_NOT_FOUND,
                    String.format("File not found: %s", file.getPath())
            );
        }
    }

    private void validateFileSize(File file) {
        if (file.length() > maxFileSize) {
            throw new DictionaryException(
                    ErrorCode.FILE_TOO_LARGE,
                    String.format("File size exceeds limit of %d bytes", maxFileSize)
            );
        }
    }

    private void validateFileReadable(File file) {
        if (!file.canRead()) {
            throw new DictionaryException(
                    ErrorCode.PERMISSION_DENIED,
                    String.format("Cannot read file: %s", file.getPath())
            );
        }
    }

    private void validateFileExtension(File file) {
        if (!file.getName().toLowerCase().endsWith(".json")) {
            throw new DictionaryException(
                    ErrorCode.INVALID_FILE_TYPE,
                    "File must be a JSON file"
            );
        }
    }

    private void validatePathSecurity(File file) {
        try {
            String canonicalPath = file.getCanonicalPath();
            String allowedPath = new File(".").getCanonicalPath();
            if (!canonicalPath.startsWith(allowedPath)) {
                throw new DictionaryException(
                        ErrorCode.PERMISSION_DENIED,
                        "Access to file outside application directory is not allowed"
                );
            }
        } catch (IOException e) {
            throw new DictionaryException(
                    ErrorCode.PERMISSION_DENIED,
                    "Unable to validate file path security"
            );
        }
    }
}
