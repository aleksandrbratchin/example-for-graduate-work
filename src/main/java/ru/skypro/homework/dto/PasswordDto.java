package ru.skypro.homework.dto;

import lombok.*;
import ru.skypro.homework.annotation.validation.Password;

/**
 * DTO для обновления пароля
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordDto {

    /**
     * текущий пароль
     */
    @Password
    private String currentPassword;
    /**
     * новый пароль
     */
    @Password
    private String newPassword;

}
