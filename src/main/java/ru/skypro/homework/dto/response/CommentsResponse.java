package ru.skypro.homework.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CommentsResponse {
    /**
     * общее количество комментариев
     */
    private Integer count;
    /**
     * {@link CommentResponse}
     */
    private List<CommentResponse> results;
}
