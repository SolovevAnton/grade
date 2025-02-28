package com.solovev.springrestsoap.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerAdviceConfig {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> exceptionHandler(NoSuchElementException ex) {
        return ResponseEntity.notFound().build();
    }
}
