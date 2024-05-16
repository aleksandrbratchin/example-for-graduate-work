package ru.skypro.homework.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import ru.skypro.homework.annotation.validation.Login;
import ru.skypro.homework.annotation.validation.*;
import ru.skypro.homework.jackson.deserializer.RoleUserDeserializer;

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
    @JsonDeserialize(using = RoleUserDeserializer.class)
    @RoleUser
    private Role role;
}
