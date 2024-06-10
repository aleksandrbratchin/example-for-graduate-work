package ru.skypro.homework.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.NewPassword;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NewPasswordTest {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 16;

    private final Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    @Test
    public void whenAllAcceptable() {
        NewPassword newPassword = NewPassword.builder()
                .currentPassword("01234567")
                .newPassword("0123456789123456")
                .build();

        Set<ConstraintViolation<NewPassword>> violations = validator.validate(newPassword);

        assertThat(violations).isEmpty();
    }

    @Nested
    class Invalid {

        @Test
        public void shouldDetectAllFieldsNull() {
            NewPassword newPassword = NewPassword.builder().build();

            Set<ConstraintViolation<NewPassword>> violations = validator.validate(newPassword);

            assertThat(violations.size()).isEqualTo(2);
            assertThat(violations).anyMatch(violation -> isViolationForField(violation, "currentPassword") && violation.getMessage().contains("пуст"));
            assertThat(violations).anyMatch(violation -> isViolationForField(violation, "newPassword") && violation.getMessage().contains("пуст"));
        }

        @Test
        public void shouldDetectAllFieldsLessThanMinimumLength() {
            NewPassword newPassword = NewPassword.builder()
                    .currentPassword("123456")
                    .newPassword("123456")
                    .build();

            Set<ConstraintViolation<NewPassword>> violations = validator.validate(newPassword);

            assertThat(violations.size()).isEqualTo(2);
            assertThat(violations).anyMatch(violation -> isViolationForField(violation, "currentPassword") && violation.getMessage().contains(String.valueOf(MIN_PASSWORD_LENGTH)));
            assertThat(violations).anyMatch(violation -> isViolationForField(violation, "newPassword") && violation.getMessage().contains(String.valueOf(MIN_PASSWORD_LENGTH)));
        }

        @Test
        public void shouldDetectAllFieldsMoreThanMaximumLength() {
            NewPassword newPassword = NewPassword.builder()
                    .currentPassword("01234567891234567")
                    .newPassword("01234567891234567")
                    .build();

            Set<ConstraintViolation<NewPassword>> violations = validator.validate(newPassword);

            assertThat(violations.size()).isEqualTo(2);
            assertThat(violations).anyMatch(violation -> isViolationForField(violation, "currentPassword") && violation.getMessage().contains(String.valueOf(MAX_PASSWORD_LENGTH)));
            assertThat(violations).anyMatch(violation -> isViolationForField(violation, "newPassword") && violation.getMessage().contains(String.valueOf(MAX_PASSWORD_LENGTH)));
        }
    }

    private boolean isViolationForField(ConstraintViolation<NewPassword> violation, String fieldName) {
        return violation.getPropertyPath().toString().equals(fieldName);
    }
}
