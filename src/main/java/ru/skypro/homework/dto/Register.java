package ru.skypro.homework.dto;

import lombok.*;
import ru.skypro.homework.annotation.validation.Login;
import ru.skypro.homework.annotation.validation.*;

/**
 * DTO для регистрации нового пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Register {
    /**
     * логин пользователя
     */
    @Login
    private String username;
    /**
     * пароль пользователя
     */
    @Password
    private String password;
    /**
     * имя пользователя
     */
    @FirstName
    private String firstName;
    /**
     * фамилия пользователя
     */
    @LastName
    private String lastName;
    /**
     * телефон пользователя
     */
    @MobilePhone
    private String phone;
    /**
     * роль пользователя
     */
    @RoleUser
    private Role role;
}
