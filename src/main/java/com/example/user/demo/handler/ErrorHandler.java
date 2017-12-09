package com.example.user.demo.handler;

import com.example.user.demo.binding.ErrorResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResource handlIllegalArgument(IllegalArgumentException e){
        return new ErrorResource("404", e.getMessage());
    }
    @ExceptionHandler
    public ErrorResource handlParseException(ParseException e){
        return new ErrorResource("415", e.getMessage());
    }
}
