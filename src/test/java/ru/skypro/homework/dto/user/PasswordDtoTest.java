package ru.skypro.homework.dto.user;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.PasswordDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    @Test
    public void whenAllAcceptable() {
        PasswordDto passwordDto = PasswordDto.builder()
                .currentPassword("01234567")
                .newPassword("0123456789123456")
                .build();

        Set<ConstraintViolation<PasswordDto>> violations = validator.validate(passwordDto);

        assertThat(violations).isEmpty();
    }


    @Nested
    class Invalid {

        @Test
        public void allNull() {
            PasswordDto passwordDto = PasswordDto.builder()
                    .build();

            Set<ConstraintViolation<PasswordDto>> violations = validator.validate(passwordDto);

            assertThat(violations.size()).isEqualTo(2);
            assertThat(violations).anyMatch(
                    testObjectConstraintViolation ->
                            testObjectConstraintViolation.getPropertyPath().toString().equals("currentPassword") &&
                                    testObjectConstraintViolation.getMessage().contains("пуст"));
            assertThat(violations).anyMatch(
                    testObjectConstraintViolation ->
                            testObjectConstraintViolation.getPropertyPath().toString().equals("newPassword") &&
                                    testObjectConstraintViolation.getMessage().contains("пуст"));
        }

        @Test
        public void allLess() {
            PasswordDto passwordDto = PasswordDto.builder()
                    .currentPassword("123456")
                    .newPassword("123456")
                    .build();

            Set<ConstraintViolation<PasswordDto>> violations = validator.validate(passwordDto);

            assertThat(violations.size()).isEqualTo(2);
            assertThat(violations).anyMatch(
                    testObjectConstraintViolation ->
                            testObjectConstraintViolation.getPropertyPath().toString().equals("currentPassword") &&
                                    testObjectConstraintViolation.getMessage().contains("8"));
            assertThat(violations).anyMatch(
                    testObjectConstraintViolation ->
                            testObjectConstraintViolation.getPropertyPath().toString().equals("newPassword") &&
                                    testObjectConstraintViolation.getMessage().contains("8"));
        }

        @Test
        public void allMore() {
            PasswordDto passwordDto = PasswordDto.builder()
                    .currentPassword("01234567891234567")
                    .newPassword("01234567891234567")
                    .build();

            Set<ConstraintViolation<PasswordDto>> violations = validator.validate(passwordDto);

            assertThat(violations.size()).isEqualTo(2);
            assertThat(violations).anyMatch(
                    testObjectConstraintViolation ->
                            testObjectConstraintViolation.getPropertyPath().toString().equals("currentPassword") &&
                                    testObjectConstraintViolation.getMessage().contains("16"));
            assertThat(violations).anyMatch(
                    testObjectConstraintViolation ->
                            testObjectConstraintViolation.getPropertyPath().toString().equals("newPassword") &&
                                    testObjectConstraintViolation.getMessage().contains("16"));
        }
    }


}