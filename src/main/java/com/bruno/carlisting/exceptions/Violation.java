package com.bruno.carlisting.exceptions;

import lombok.Getter;

@Getter
public class Violation extends StandardErrorResponse {

    private final String objectName;
    private final String fieldName;
    private final Object value;

    public Violation(String timestamp, Integer status, String exception, String objectName, String fieldName,
                     Object value, String message) {
        super(timestamp, status, exception, message);
        this.objectName = objectName;
        this.fieldName = fieldName;
        this.value = value;
    }
}
