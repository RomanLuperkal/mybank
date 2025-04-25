package org.ivanov.account.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateAccountDto {
    @NotBlank(message = "логин не может быть пустым")
    private String username;
    @NotBlank(message = "пароль не может быть пустым")
    private String password;
    @Email(message = "адрес почти должен иметь корректный формат")
    private String email;
    @NotBlank(message = "имя не может быть пустым")
    private String firstName;
    @NotBlank(message = "фамилия не может быть пустой")
    private String lastName;
    @NotNull
    private LocalDate birthDate;
}
