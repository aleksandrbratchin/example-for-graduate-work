package ru.skypro.homework.annotation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotNull
@ReportAsSingleViolation
@Constraint(validatedBy = RoleUserValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleUser {
    String message() default "Неверная роль";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
