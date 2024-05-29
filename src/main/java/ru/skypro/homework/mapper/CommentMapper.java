package ru.skypro.homework.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.response.CommentResponse;
import ru.skypro.homework.dto.response.CommentsResponse;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import java.util.List;

@Mapper(componentModel = "spring", uses = {User.class})
public abstract class CommentMapper {

    @Mappings({
            @Mapping(target = "author", source = "user.id"),
            @Mapping(target = "authorImage", source = "user.avatar.id"),
            @Mapping(target = "authorFirstName", source = "user.firstName"),
            @Mapping(target = "createdAt", source = "createdAt.nano"),
            @Mapping(target = "pk", source = "id")
    })
    public abstract CommentResponse toCommentResponse(Comment comment);


    @Mappings({
            @Mapping(target = "id", source = "pk")
    })
    public abstract Comment toComment(CommentResponse commentResponse);


    public abstract CreateOrUpdateComment toCreateOrUpdateComment(Comment comment);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
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

}


