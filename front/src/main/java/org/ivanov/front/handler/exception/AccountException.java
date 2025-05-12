package org.ivanov.front.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountException extends RuntimeException {
    private String message;
    private String status;
}
