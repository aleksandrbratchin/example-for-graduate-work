package ru.skypro.homework.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends ParentIDEntity {
    /**
     * id автора комментария
     */
    @ManyToOne
    @JoinColumn(name= "author")
    private User author;
//    /**
//     * ссылка на аватар автора комментария
//     */
//    @Column(name= "authorImage")
//    private String authorImage;
//    /**
//     * имя создателя комментария
//     */
//    @Column(name= "authorFirstName")
//    private String authorFirstName;
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

    @Builder

    public Comment(Long id, User author, Integer createdAt, String text) {
        super(id);
        this.author = author;
        this.createdAt = createdAt;
        this.text = text;
    }
}
