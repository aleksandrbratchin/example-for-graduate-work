package ru.skypro.homework.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment")
public class Comment extends ParentIDEntity {
    /**
     * id автора комментария
     */
    @Column(name= "author_id")
    private Integer author;
    /**
     * ссылка на аватар автора комментария
     */
    @Column(name= "authorImage")
    private String authorImage;
    /**
     * имя создателя комментария
     */
    @Column(name= "authorFirstName")
    private String authorFirstName;
    /**
     * дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
     */
    @Column(name= "createdAt")
    private Integer createdAt;

    /**
     * текст комментария
     */
    @Column(name= "text")
    private String text;
}
