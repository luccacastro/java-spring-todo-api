package com.example.tasklist.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorMessage = ex.getMessage();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("errors", errorMessage);

        return ResponseEntity.badRequest().body(response);
    }
}