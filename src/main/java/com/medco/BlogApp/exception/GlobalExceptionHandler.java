package com.medco.BlogApp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleExceptions(Exception ex) {
        log.error("Unhandled exception caught by GlobalExceptionHandler:", ex);
        CustomError customError = new CustomError();
        customError.setErrorCode("GENERIC_ERROR");
        customError.setErrorMessage("An unexpected error occurred");
        customError.setErrorDetails(new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                Arrays.toString(ex.getStackTrace())
        ));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customError);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<CustomError> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        CustomError customError = new CustomError();
        customError.setErrorCode("EMAIL_ALREADY_EXISTS");
        customError.setErrorMessage(ex.getMessage());
        customError.setErrorDetails(new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                "The email address is already registered."
        ));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(customError); // 409 Conflict
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomError> handleUserNotFound(UserNotFoundException ex) {
        CustomError customError = new CustomError();
        customError.setErrorCode("USER_NOT_FOUND");
        customError.setErrorMessage(ex.getMessage());
        customError.setErrorDetails(new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                "The user with the specified ID was not found."
        ));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError); // 404 Not Found
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<CustomError> handlePasswordMismatch(PasswordMismatchException ex) {
        CustomError customError = new CustomError();
        customError.setErrorCode("PASSWORD_MISMATCH");
        customError.setErrorMessage(ex.getMessage());
        customError.setErrorDetails(new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                "Passwords provided do not match."
        ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customError); // 400 Bad Request
    }

    @ExceptionHandler(InvalidResetCodeException.class)
    public ResponseEntity<CustomError> handleInvalidResetCode(InvalidResetCodeException ex) {
        CustomError customError = new CustomError();
        customError.setErrorCode("INVALID_RESET_CODE");
        customError.setErrorMessage(ex.getMessage());
        customError.setErrorDetails(new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                "The reset code provided is invalid or expired."
        ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customError); // 400 Bad Request
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomError> handleMissingParams(MissingServletRequestParameterException ex) {
        CustomError error = new CustomError(
                "Missing Request Parameter",
                ex.getParameterName() + " is required.",
                null  // 👈 third argument (ErrorDetails) can be null
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleValidationErrors(MethodArgumentNotValidException ex) {
        var fieldError = ex.getBindingResult().getFieldErrors().get(0);
        String errorCode    = "VALIDATION_ERROR";
        String errorMessage = fieldError.getDefaultMessage();
        CustomError err = new CustomError(errorCode, errorMessage, null);
        return ResponseEntity.badRequest().body(err);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> handleResourceNotFound(ResourceNotFoundException ex) {
        CustomError customError = new CustomError();
        customError.setErrorCode("RESOURCE_NOT_FOUND");
        customError.setErrorMessage(ex.getMessage());
        customError.setErrorDetails(new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                "The requested resource could not be found."
        ));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError); // 404
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomError> handleUnauthorized(UnauthorizedException ex) {
        CustomError customError = new CustomError();
        customError.setErrorCode("FORBIDDEN");
        customError.setErrorMessage(ex.getMessage());
        customError.setErrorDetails(new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                "You are not allowed to perform this action."
        ));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customError);
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public ResponseEntity<CustomError> handleJpaRetrievalFailure(JpaObjectRetrievalFailureException ex) {
        CustomError err = new CustomError();
        err.setErrorCode("USER_NOT_FOUND");
        err.setErrorMessage("The requested user does not exist");
        err.setErrorDetails(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<CustomError> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
        CustomError err = new CustomError();
        err.setErrorCode("UNSUPPORTED_MEDIA_TYPE");
        err.setErrorMessage("Unsupported content type: " + ex.getContentType());
        err.setErrorDetails(null);
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)  // 415
                .body(err);
    }

}