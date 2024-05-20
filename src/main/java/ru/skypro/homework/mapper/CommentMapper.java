package ru.skypro.homework.mapper;

import jakarta.persistence.Column;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.skypro.homework.dto.response.CommentResponse;
import ru.skypro.homework.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mappings({
            @Mapping(target = "author", source = "model.comment.author"),
            @Mapping(target = "authorImage", source = "model.comment.authorImage"),
            @Mapping(target = "authorFirstName", source = "model.comment.authorFirstName"),
            @Mapping(target = "createdAt", source = "model.comment.createdAt"),
            @Mapping(target = "pk", source = "model.comment.pk"),
            @Mapping(target = "text", source ="model.comment.text" )
    }
    )

    CommentResponse toCommentResponse (Comment comment);
    @Mappings({
            @Mapping(target = "author", source = "model.comment.author"),
            @Mapping(target = "authorImage", source = "model.comment.authorImage"),
            @Mapping(target = "authorFirstName", source = "model.comment.authorFirstName"),
            @Mapping(target = "createdAt", source = "model.comment.createdAt"),
            @Mapping(target = "pk", source = "model.comment.pk"),
            @Mapping(target = "text", source ="model.comment.text" )
    }
    )
    Comment toComment (CommentResponse commentResponse);
}


