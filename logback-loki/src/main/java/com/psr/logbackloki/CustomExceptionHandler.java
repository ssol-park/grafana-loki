package com.psr.logbackloki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        try {
            MDC.put("exceptionClass", e.getClass().getSimpleName());

            logger.error("An exception occurred: {}", e.getMessage(), e);

            return ResponseEntity.status(200).body(e.getMessage());
        } finally {
            MDC.clear();
        }
    }
}
