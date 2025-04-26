package org.ivanov.accountdto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateAccountDto(
        @NotBlank(message = "логин не может быть пустым")
        String username,
        @NotBlank(message = "пароль не может быть пустым")
        String password,
        @Email(message = "адрес почти должен иметь корректный формат")
        String email,
        @NotBlank(message = "имя не может быть пустым")
        String firstName,
        @NotBlank(message = "фамилия не может быть пустой")
        String lastName,
        @NotNull
        LocalDate birthDate
) {
}
