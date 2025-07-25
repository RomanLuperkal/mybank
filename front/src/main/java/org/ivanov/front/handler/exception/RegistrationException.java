package org.ivanov.front.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ivanov.accountdto.account.CreateAccountDto;

@AllArgsConstructor
@Getter
public class RegistrationException extends RuntimeException {
    private CreateAccountDto dto;
    private String message;
}
