package ru.skypro.homework.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AdsResponse {
    /**
     * общее количество объявлений
     */
    private Integer count;
    /**
     * {@link AdResponse}
     */
    private List<AdResponse> results;
}
