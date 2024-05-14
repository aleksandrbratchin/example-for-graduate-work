package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.annotation.validation.Password;

@Data
public class Login {
    /**
     * логин пользователя
     */
    @ru.skypro.homework.annotation.validation.Login
    private String username;
    /**
     * пароль пользователя
     */
    @Password
    private String password;
}
