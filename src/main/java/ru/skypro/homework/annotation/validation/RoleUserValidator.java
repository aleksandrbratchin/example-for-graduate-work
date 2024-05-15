package ru.skypro.homework.annotation.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.skypro.homework.dto.Role;


public class RoleUserValidator implements ConstraintValidator<RoleUser, Role> {

    @Override
    public void initialize(RoleUser constraintAnnotation) {
    }

    @Override
    public boolean isValid(Role value, ConstraintValidatorContext context) {
        try {
            Role.valueOf(value.name());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

}
