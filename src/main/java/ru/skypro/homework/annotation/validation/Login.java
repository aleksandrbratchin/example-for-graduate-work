package ru.skypro.homework.annotation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotBlank(message = "Логин не может быть пуст")
@Size(min = 4, message = "Логин должен содержать минимум 4 символа")
@Size(max = 32, message = "Логин должен содержать максимум 32 символа")
@Constraint(validatedBy = {})
@Documented
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface Login {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
