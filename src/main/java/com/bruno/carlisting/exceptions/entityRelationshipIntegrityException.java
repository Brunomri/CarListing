package com.bruno.carlisting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class entityRelationshipIntegrityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public entityRelationshipIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }

    public entityRelationshipIntegrityException(String message) {
        super(message);
    }
}
