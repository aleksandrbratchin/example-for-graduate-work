package ru.skypro.homework.dto;

import lombok.*;

import javax.validation.constraints.*;

/**
 * DTO для создания и обновления объявления
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateAd {
    /**
     * заголовок объявления
     */
    @NotBlank(message = "Заголовок не может быть пуст")
    @Size(min = 4, message = "Заголовок должен содержать минимум 4 символа")
    @Size(max = 32, message = "Заголовок должен содержать максимум 32 символа")
    private String title;
    /**
     * цена объявления
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 10000000)
    private Integer price;
    /**
     * описание объявления
     */
    @NotBlank(message = "Необходимо заполнить описание")
    @Size(min = 8, message = "Описание должно содержать минимум 8 символов")
    @Size(max = 64, message = "Описание должно содержать максимум 64 символа")
    private String description;
}
