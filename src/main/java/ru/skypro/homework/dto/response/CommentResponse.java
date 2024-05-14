package ru.skypro.homework.dto.response;

import lombok.Data;

@Data
public class CommentResponse {
    /**
     * id автора комментария
     */
    private Integer author;
    /**
     * ссылка на аватар автора комментария
     */
    private String authorImage;
    /**
     * имя создателя комментария
     */
    private String authorFirstName;
    /**
     * дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
     */
    private Integer createdAt;
    /**
     * id комментария
     */
    private Integer pk;
    /**
     * текст комментария
     */
    private String text;
}
