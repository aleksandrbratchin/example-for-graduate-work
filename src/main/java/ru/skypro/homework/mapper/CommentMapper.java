//package ru.skypro.homework.mapper;
//
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//import ru.skypro.homework.dto.CreateOrUpdateComment;
//import ru.skypro.homework.dto.response.CommentResponse;
//import ru.skypro.homework.dto.response.CommentsResponse;
//import ru.skypro.homework.model.Comment;
//import ru.skypro.homework.model.User;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring", uses = User.class)
//public interface CommentMapper {
//
//
//    @Mappings({
//            @Mapping(target = "author", source = "author.id"),
//            @Mapping(target = "authorImage", source = "author.avatar.id"),
//            @Mapping(target = "authorFirstName", source = "author.firstName"),
//            @Mapping(target = "createdAt", source = "createdAt"),
//            @Mapping(target = "pk", source = "id"),
//            @Mapping(target = "text", source ="text" )
//    }
//    )
//
//    CommentResponse toCommentResponse (Comment comment);
//    @Mappings({
//            @Mapping(target = "author",expression = "java(User.builder().build())"),
//            @Mapping(target = "createdAt", source = "createdAt"),
//            @Mapping(target = "id", source = "pk"),
//            @Mapping(target = "text", source ="text" )
//    }
//    )
//    Comment toComment (CommentResponse commentResponse);
//
//
//    CreateOrUpdateComment toCreateOrUpdateComment (Comment comment);
//    @Mappings({
//            @Mapping(target = "author", ignore = true),
//            @Mapping(target = "id", ignore = true),
//            @Mapping(target = "text", source = "text"),
//            @Mapping(target = "createdAt", ignore = true)
//    })
//    Comment toComment (CreateOrUpdateComment createOrUpdateComment);
//    List<CommentResponse> resultsToResultsDtos (List <CommentResponse> results);
//
//    Integer countToDto (Integer count);
//
//    @Mappings(
//            {
//                    @Mapping(target = "results", source = "resultsToResultsDtos"),
//                    @Mapping(target = "count", source = "countToDto")
//            }
//    )
//    CommentsResponse toCommentsResponse(Comment comment);
//
//}
//
//
