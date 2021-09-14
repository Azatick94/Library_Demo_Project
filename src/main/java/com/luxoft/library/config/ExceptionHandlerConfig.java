package com.luxoft.library.config;

import com.luxoft.library.utils.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// https://dzone.com/articles/best-practice-for-exception-handling-in-spring-boo
public class ExceptionHandlerConfig {

    @ExceptionHandler(value = { NotFoundException.class })
    public ResponseEntity<Object> handleUnauthorizedException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
