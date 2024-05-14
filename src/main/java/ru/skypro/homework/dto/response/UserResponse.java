package ru.skypro.homework.dto.response;

import lombok.Data;
import ru.skypro.homework.dto.Role;

@Data
public class UserResponse {
    /**
     * id пользователя
     */
    private Integer id;
    /**
     * логин пользователя
     */
    private String username;
    /**
     * пароль пользователя
     */
    private String password;
    /**
     * имя пользователя
     */
    private String firstName;
    /**
     * фамилия пользователя
     */
    private String lastName;
    /**
     * телефон пользователя
     */
    private String phone;
    /**
     * роль пользователя
     */
    private Role role;
    /**
     * ссылка на аватар пользователя
     */
    private String image;
}
