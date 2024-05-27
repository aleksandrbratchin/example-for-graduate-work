package ru.skypro.homework.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ads")
@EntityListeners(AuditingEntityListener.class)
public class Ad extends ParentIDEntity {

    /**
     * цена объявления
     */
    @Column(name = "price")
    private Integer price;
    /**
     * заголовок объявления
     */
    @Column(name = "title")
    private String title;

    /**
     * описание объявления
     */
    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany()
    @JoinColumn(name = "comment_id")
    private List<Comment> comments;

}
