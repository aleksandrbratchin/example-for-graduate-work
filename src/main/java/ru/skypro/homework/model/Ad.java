package ru.skypro.homework.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ads")
public class Ad extends ParentIDEntity {

    /**
     * id объявления
     */
    @Column(name = "pk")
    private Integer pk;
    /**
     * имя автора объявления
     */
    @Column(name = "authorFirstName")
    private String authorFirstName;
    /**
     * фамилия автора объявления
     */
    @Column(name = "authorLastName")
    private String authorLastName;
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
}
