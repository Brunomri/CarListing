package com.bruno.carlisting.exceptions;

public class entityRelationshipIntegrityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public entityRelationshipIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }

    public entityRelationshipIntegrityException(String message) {
        super(message);
    }
}
