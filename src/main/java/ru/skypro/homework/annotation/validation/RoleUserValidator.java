package ru.skypro.homework.annotation.validation;


import ru.skypro.homework.dto.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
