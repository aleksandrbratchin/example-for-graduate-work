package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.response.CommentResponse;
import ru.skypro.homework.dto.response.CommentsResponse;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class CommentMapperTest {
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    private User userTest;
    private Comment commentTest;

    private LocalDateTime dateTest;

    public void setDateTest(LocalDateTime dateTest) {
        this.dateTest = dateTest;
    }

    @BeforeEach
    public void setUp() {
        userTest = new User();
        userTest.setId(1L);
        userTest.setUsername("test");

        setDateTest(LocalDateTime.now());



        commentTest = Comment.builder()
                .id(1L)
                .text("testtesttest")
                .user(userTest)
                .createdAt(dateTest)
                .build();
    }

    @Test
    void equalityCommentAndCommentResponse() {


        CommentResponse exp = new CommentResponse();
        exp.setPk(1);
        exp.setText("testtesttest");
        exp.setAuthor(userTest.getId().intValue());
        exp.setCreatedAt(dateTest.getNano());

        CommentResponse aq = commentMapper.toCommentResponse(commentTest);



        assertThat(aq.getPk()).isEqualTo(exp.getPk());
        assertThat(aq.getText()).isEqualTo(exp.getText());
        assertThat(aq.getAuthor()).isEqualTo(exp.getAuthor());
        assertThat(aq.getCreatedAt()).isEqualTo(exp.getCreatedAt());

    }

    @Test
    void equalityCommentAndCreateOrUpdateComment() {

        CreateOrUpdateComment exp = new CreateOrUpdateComment("testtesttest");

        CreateOrUpdateComment aq=commentMapper.toCreateOrUpdateComment(commentTest);

        assertThat(aq.getText()).isEqualTo(exp.getText());
    }

    @Test
    void toListWithDto() {

        List<Comment> base = List.of(commentTest);

        CommentResponse commentResponseTest = new CommentResponse();
        commentResponseTest.setPk(1);
        commentResponseTest.setText("testtesttest");
        commentResponseTest.setAuthor(userTest.getId().intValue());
        commentResponseTest.setCreatedAt(dateTest.getNano());


        List<CommentResponse> aq = commentMapper.toListWithDto(base);

        List<CommentResponse> exp = List.of(commentResponseTest);

        assertThat(aq.size()).isEqualTo(exp.size());
        assertThat(aq).isEqualTo(exp);

    }

    @Test
    void equalityOfCommentsResponse() {
        CommentResponse commentResponseTest = new CommentResponse();
        commentResponseTest.setPk(1);
        commentResponseTest.setText("testtesttest");
        commentResponseTest.setAuthor(userTest.getId().intValue());
        commentResponseTest.setCreatedAt(dateTest.getNano());

        List<Comment> base = List.of(commentTest);

        List<CommentResponse> second = List.of(commentResponseTest);

        int countExp = 1;

        CommentsResponse commentsResponseDTO = commentMapper.toCommentsResponse(base);

        CommentsResponse exp = new CommentsResponse();
        exp.setResults(second);
        exp.setCount(second.size());

        assertThat(base.size()).isEqualTo(countExp);
        assertThat(commentsResponseDTO).isEqualTo(exp);
    }
}