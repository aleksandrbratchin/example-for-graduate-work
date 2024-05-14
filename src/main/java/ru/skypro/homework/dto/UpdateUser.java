package ru.skypro.homework.dto;

import lombok.*;
import ru.skypro.homework.annotation.validation.FirstName;
import ru.skypro.homework.annotation.validation.LastName;
import ru.skypro.homework.annotation.validation.MobilePhone;

/**
 * DTO для изменения пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUser {
    /**
     * имя пользователя
     */
    @FirstName //todo почему в тз другие ограничения?
    private String firstName;
    /**
     * фамилия пользователя
     */
    @LastName //todo почему в тз другие ограничения?
    private String lastName;
    /**
     * телефон пользователя
     */
    @MobilePhone
    private String phone;
}
