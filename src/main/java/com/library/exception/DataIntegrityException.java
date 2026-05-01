package com.library.exception;

/**
 * Custom exception thrown when there is a data integrity violation
 * (e.g. duplicate ISBN, missing required relationship).
 */
public class DataIntegrityException extends RuntimeException {

    public DataIntegrityException(String message) {
        super(message);
    }

    public DataIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
}
