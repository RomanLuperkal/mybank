package org.ivanov.accountdto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
public class ValidBirthdateValidator implements ConstraintValidator<ValidBirthdate, LocalDate> {
    private String fieldName;

    @Override
    public void initialize(ValidBirthdate constraintAnnotation) {
       fieldName = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        try {
            if (birthDate == null) {
                return true;
            }
            boolean isValid = true;
            LocalDate today = LocalDate.now();
            Period period = Period.between(birthDate, today);
            if (birthDate.isAfter(today) || !(period.getYears() >= 18)) {
                isValid = false;
            }

            return isValid;
        } catch (Exception e) {
            log.error("Ошибка обработки в ValidBirthdateValidator");
            return false;
        }

    }
}
