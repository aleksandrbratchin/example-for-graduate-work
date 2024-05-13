package ru.skypro.homework.dto.user;


import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.RegisterDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    @Test
    public void whenAllAcceptable() {
        RegisterDto registerDto = RegisterDto.builder()
                .username("login")
                .password("Password")
                .firstName("Иван")
                .lastName("Иванов")
                .phone("+7 (123) 456-78-90")
                .role("ADMIN")
                .build();

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations).isEmpty();
    }

}