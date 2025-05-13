package com.medco.BlogApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {
    private String errorCode;
    private String errorMessage;
    private ErrorDetails errorDetails;
}
