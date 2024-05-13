package ru.skypro.homework.annotation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull
@Pattern(regexp = "^(USER)|(ADMIN)$")
@Constraint(validatedBy = {})
//@ReportAsSingleViolation меняет поведение на следующее: проверка выполняется до нарушения первого констрейна, после нарушения будет отдана ошибка (поле message()) из кастомной аннотации.
@ReportAsSingleViolation
@Documented
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface RoleUser {
    String message() default "Неизвестная роль";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
