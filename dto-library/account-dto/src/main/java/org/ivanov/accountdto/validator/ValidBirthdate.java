package org.ivanov.accountdto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidBirthdateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBirthdate {
    String message() default "Пользователю должно быть не меньше 18 лет";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String field();
}
