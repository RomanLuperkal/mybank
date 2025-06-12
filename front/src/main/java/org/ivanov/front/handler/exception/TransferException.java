package org.ivanov.front.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class TransferException extends RuntimeException {
    private String message;
    private HttpStatus status;
}
