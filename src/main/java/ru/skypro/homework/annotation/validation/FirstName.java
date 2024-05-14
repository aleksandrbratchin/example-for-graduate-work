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

@NotBlank(message = "Имя не может быть пустым")
@Size(min = 2, message = "Имя должно содержать минимум 2 символа")
@Size(max = 16, message = "Имя должно содержать максимум 16 символов")

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface FirstName {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
