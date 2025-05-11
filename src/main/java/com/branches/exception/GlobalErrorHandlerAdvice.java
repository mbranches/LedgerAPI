package com.branches.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandlerAdvice {
    public ResponseEntity<DefaultErrorMessage> handleInternalServerErrorException(InternalServerErrorException e) {
        DefaultErrorMessage error = new DefaultErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getReason());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
