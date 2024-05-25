package ru.skypro.homework.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ads")
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


//    /**
//     * имя автора объявления
//     */
//    @Column(name = "authorFirstName")
//    private String authorFirstName;
//    /**
//     * фамилия автора объявления
//     */
//    @Column(name = "authorLastName")
//    private String authorLastName;
//    /**
//     * убрать
//     * описание объявления
//     */
//    @Column(name = "name")
//    private String name;
//    /**
//     * убрать
//     * логин автора объявления
//     */
//    @Column(name = "email")
//    private String email;
//    /**
//     * убрать
//     * ссылка на картинку объявления
//     */
//    @Column(name = "image")
//    private String image;
//    /**
//     * телефон автора объявления
//     */
//    @Column(name = "phone")
//    private String phone;


//    @Builder
//    public Ad(Long id, String authorFirstName, String authorLastName, String name, String email, String image, String phone, Integer price, String title) {
//        super(id);
//        this.authorFirstName = authorFirstName;
//        this.authorLastName = authorLastName;
//        this.name = name;
//        this.email = email;
//        this.image = image;
//        this.phone = phone;
//        this.price = price;
//        this.title = title;
//    }
}
