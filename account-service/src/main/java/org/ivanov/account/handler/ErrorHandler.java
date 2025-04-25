package org.ivanov.account.handler;

import org.ivanov.account.handler.exception.AccountException;
import org.ivanov.account.handler.response.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(AccountException.class)
    private ResponseEntity<ApiError> handleException(AccountException e) {
        ApiError errorResponse = ApiError.builder()
                .message(e.getMessage())
                .status(e.getStatus().toString())
                .build();
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }
}
