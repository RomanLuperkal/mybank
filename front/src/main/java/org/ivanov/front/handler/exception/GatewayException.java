package org.ivanov.front.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class GatewayException extends RuntimeException {
    private String message;
    private HttpStatus status;
}
