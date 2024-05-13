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
public class RegisterDto {
    /**
     * логин
     */
    @Login
    private String username;
    /**
     * пароль
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
    private String role;
}
