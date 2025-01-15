package com.coffeecode.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LoggedException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(LoggedException.class);

    protected LoggedException(String message) {
        super(message);
        logger.error(message);
    }

    protected LoggedException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}
