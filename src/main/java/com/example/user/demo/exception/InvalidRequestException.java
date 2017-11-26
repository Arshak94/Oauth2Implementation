package com.example.user.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

public class InvalidRequestException extends RuntimeException {
    private Errors errors;
    private HttpStatus httpStatus;

    public InvalidRequestException(String massage, Errors errors, HttpStatus httpStatus){
        super(massage);
        this.errors = errors;
        this.httpStatus = httpStatus;
    }

    public InvalidRequestException(String message, Errors errors) {
        this(message, errors, HttpStatus.BAD_REQUEST);
    }

    public InvalidRequestException(String message) {
        this(message, (Errors)null, HttpStatus.BAD_REQUEST);
    }

    public InvalidRequestException(String massage, HttpStatus httpStatus){
        this(massage, (Errors)null, httpStatus);
    }

    public Errors getErrors() {
        return this.errors;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
