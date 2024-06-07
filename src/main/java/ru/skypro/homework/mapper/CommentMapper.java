package ru.skypro.homework.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.response.CommentResponse;
import ru.skypro.homework.dto.response.CommentsResponse;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {User.class})
public abstract class CommentMapper {

    @Value("${download.url}")
    protected String downloadUrl;

    @Mapping(target = "author", source = "user.id")
    @Mapping(
            target = "authorImage",
            expression = "java(comment.getUser().getAvatar() == null ? \"\" : downloadUrl + comment.getUser().getAvatar().getId())"
    )
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "createdAt", expression = "java(toEpochMilli(comment.getCreatedAt()))")
    @Mapping(target = "pk", source = "id")
    public abstract CommentResponse toCommentResponse(Comment comment);

    public abstract CreateOrUpdateComment toCreateOrUpdateComment(Comment comment);

    @Mapping(target = "id", ignore = true)
    public abstract Comment toComment(CreateOrUpdateComment createOrUpdateComment);

    protected List<CommentResponse> toListWithDto(List<Comment> comments) {
        return comments
                .stream()
                .map(this::toCommentResponse)
                .toList();
    }

    public CommentsResponse toCommentsResponse(List<Comment> comments) {
        CommentsResponse commentsResponse = new CommentsResponse();
        commentsResponse.setCount(comments.size());
        commentsResponse.setResults(toListWithDto(comments));
        return commentsResponse;
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return zonedDateTime.toInstant().toEpochMilli();
    }

}


