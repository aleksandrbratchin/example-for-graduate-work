package ru.skypro.homework.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends ParentIDEntity {

    /**
     * ссылка на автора комментария
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
     */
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    /**
     * текст комментария
     */
    @Column(name = "text")
    private String text;

    @Builder
    public Comment(Long id, User user, LocalDateTime createdAt, String text) {
        super(id);
        this.user = user;
        this.createdAt = createdAt;
        this.text = text;
    }

}
