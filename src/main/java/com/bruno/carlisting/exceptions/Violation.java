package com.bruno.carlisting.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Violation {

    private final String timestamp;
    private final Integer status;
    private final String exception;
    private final String objectName;
    private final String fieldName;
    private final Object value;
    private final String message;
}
