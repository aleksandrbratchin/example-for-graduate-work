package ru.skypro.homework.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ads")
public class Ad extends ParentIDEntity {


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
    /**
     * описание объявления
     */
    @Column(name = "name")
    private String name;
    /**
     * логин автора объявления
     */
    @Column(name = "email")
    private String email;
    /**
     * ссылка на картинку объявления
     */
    @Column(name = "image")
    private String image;
    /**
     * телефон автора объявления
     */
    @Column(name = "phone")
    private String phone;
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

    @Builder
    public Ad(Long id, String authorFirstName, String authorLastName, String name, String email, String image, String phone, Integer price, String title) {
        super(id);
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.name = name;
        this.email = email;
        this.image = image;
        this.phone = phone;
        this.price = price;
        this.title = title;
    }
}
