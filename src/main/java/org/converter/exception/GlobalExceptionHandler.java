package org.converter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorInfo> handleException(NoSuchElementException ex) {
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage());
        HttpStatus status = HttpStatus.NO_CONTENT;
        return createResponse(errorInfo, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> handleException(IllegalArgumentException ex) {
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return createResponse(errorInfo, status);
    }

    private ResponseEntity<ErrorInfo> createResponse(ErrorInfo errorInfo, HttpStatus status) {
        return ResponseEntity.status(status).body(errorInfo);
    }
}
