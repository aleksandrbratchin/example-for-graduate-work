package ru.skypro.homework.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO для создания и обновления комментариев
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateComment {
    /**
     * текст комментария
     */
    @NotBlank(message = "Комментарий не может быть пуст")
    @Size(min = 8, message = "Комментарий должен содержать минимум 8 символов")
    @Size(max = 64, message = "Комментарий должен содержать максимум 64 символа")
    private String text;
}
