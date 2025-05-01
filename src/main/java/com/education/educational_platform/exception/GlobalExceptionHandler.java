package com.education.educational_platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
        return new ResponseEntity(new Error(exception.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Error> invalidRequestExceptionHandler(InvalidRequestException exception){
        return new ResponseEntity<>(new Error(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
