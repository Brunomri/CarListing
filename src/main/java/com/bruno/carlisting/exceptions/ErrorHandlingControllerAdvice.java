package com.bruno.carlisting.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        var error = new ValidationErrorResponse();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(formatter.format(LocalDateTime.now()), HttpStatus.BAD_REQUEST.value(),
                        violation.getClass().getName(), violation.getRootBeanClass().getName(),
                        violation.getPropertyPath().toString(), violation.getInvalidValue(), violation.getMessage()));
        }
        log.warn("Constraint validation exception occurred:", e);
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(new Violation(formatter.format(LocalDateTime.now()),
                    HttpStatus.BAD_REQUEST.value(), fieldError.getClass().getName(), fieldError.getObjectName(),
                    fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
        }
        log.warn("Method argument not valid exception occurred:", e);
        return error;
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    StandardErrorResponse onObjectNotFoundException(ObjectNotFoundException e) {
        log.warn("Object not found exception occurred:", e);
        return new StandardErrorResponse(formatter.format(LocalDateTime.now()),
                HttpStatus.NOT_FOUND.value(), e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(entityRelationshipIntegrityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    StandardErrorResponse entityRelationshipIntegrityException(entityRelationshipIntegrityException e) {
        log.warn("Entity relationship integrity exception occurred:", e);
        return new StandardErrorResponse(formatter.format(LocalDateTime.now()),
                HttpStatus.BAD_REQUEST.value(), e.getClass().getName(), e.getMessage());
    }
}
