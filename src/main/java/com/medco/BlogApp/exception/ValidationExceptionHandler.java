package com.medco.BlogApp.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomError> handleValidationExceptions(ConstraintViolationException ex){
        CustomError customError = new CustomError();
        customError.setErrorCode("VALIDATION_ERROR");
        customError.setErrorMessage("validation failed.");
        customError.setErrorDetails(new ErrorDetails(LocalDateTime.now(), ex.getMessage(), ex.getConstraintViolations().toString()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customError);
    }
}
