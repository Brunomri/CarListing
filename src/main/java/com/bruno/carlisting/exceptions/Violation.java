package com.bruno.carlisting.exceptions;

public class Violation {

    private final String objectName;
    private final String fieldName;
    private final Object value;
    private final String message;

    public Violation(String objectName, String fieldName, Object value, String message) {
        this.objectName =  objectName;
        this.fieldName = fieldName;
        this.value = value;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
