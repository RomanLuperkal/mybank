package org.ivanov.accountdto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ivanov.accountdto.validator.ValidBirthdate;

import java.time.LocalDate;

public record UpdateProfileDto(
                               @NotBlank(message = "имя не может быть пустым")
                               String firstName,
                               @NotBlank(message = "фамилия не может быть пустой")
                               String lastName,
                               @Email(message = "адрес почты должен иметь корректный формат",
                                       regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
                               String email,
                               @ValidBirthdate(field = "birthDate")
                               @NotNull
                               LocalDate birthDate) {
}
