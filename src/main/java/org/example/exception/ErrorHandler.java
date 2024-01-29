package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.exceptions.ConflictException;
import org.example.exception.exceptions.NotFoundException;
import org.example.exception.exceptions.SqlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleNotFoundException(final NotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleConflictException(final ConflictException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentException(final IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleSqlException(final SqlException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
