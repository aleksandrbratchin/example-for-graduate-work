package ru.skypro.homework.dto.response;

import lombok.Data;

@Data
public class AdResponse {
    /**
     * id объявления
     */
    private Integer pk;
    /**
     * id автора объявления
     */
    private Integer author;
    /**
     * ссылка на картинку объявления
     */
    private String image;
    /**
     * цена объявления
     */
    private Integer price;
    /**
     * заголовок объявления
     */
    private String title;
}
