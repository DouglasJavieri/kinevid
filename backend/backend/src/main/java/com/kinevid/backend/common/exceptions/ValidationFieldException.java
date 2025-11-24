package com.kinevid.backend.common.exceptions;

public class ValidationFieldException extends RuntimeException {
    public ValidationFieldException(String message, Throwable cause) {
        super(message,cause);
    }
    public ValidationFieldException(String message) {
        super(message);
    }
}
