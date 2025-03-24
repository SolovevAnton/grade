package com.solovev.kiteshop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noElement(NoSuchElementException e) {
        log.info("Object not found exception: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }
}
