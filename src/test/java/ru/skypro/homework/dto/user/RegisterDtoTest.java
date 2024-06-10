package ru.skypro.homework.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void whenAllFieldsValid_thenReturnNoViolations() {
        Register registerDto = Register.builder()
                .username("login@a.r")
                .password("Password")
                .firstName("Иван")
                .lastName("Иванов")
                .phone("+7 (123) 456-78-90")
                .role(Role.USER)
                .build();

        Set<ConstraintViolation<Register>> violations = validator.validate(registerDto);

        assertThat(violations).isEmpty();
    }

    @Nested
    class Invalid {

        @Test
        public void whenAllFieldsNull_thenReturnViolations() {
            Register registerDto = new Register();

            Set<ConstraintViolation<Register>> violations = validator.validate(registerDto);

            assertThat(violations.size()).isEqualTo(6);
            assertThat(violations).anyMatch(violation ->
                    violation.getPropertyPath().toString().equals("username") &&
                            violation.getMessage().contains("пуст") &&
                            violation.getMessage().toLowerCase().contains("логин")
            );
            assertThat(violations).anyMatch(violation ->
                    violation.getPropertyPath().toString().equals("password") &&
                            violation.getMessage().contains("пуст") &&
                            violation.getMessage().toLowerCase().contains("пароль")
            );
            assertThat(violations).anyMatch(violation ->
                    violation.getPropertyPath().toString().equals("firstName") &&
                            violation.getMessage().contains("пуст") &&
                            violation.getMessage().toLowerCase().contains("имя")
            );
            assertThat(violations).anyMatch(violation ->
                    violation.getPropertyPath().toString().equals("lastName") &&
                            violation.getMessage().contains("пуст") &&
                            violation.getMessage().toLowerCase().contains("фамилия")
            );
            assertThat(violations).anyMatch(violation ->
                    violation.getPropertyPath().toString().equals("phone") &&
                            violation.getMessage().toLowerCase().contains("телефон")
            );
            assertThat(violations).anyMatch(violation ->
                    violation.getPropertyPath().toString().equals("role") &&
                            violation.getMessage().contains("Неверная роль")
            );
        }

    }

}
