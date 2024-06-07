package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.response.CommentResponse;
import ru.skypro.homework.dto.response.CommentsResponse;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;
    private User testUser;
    private Comment testComment;

    @BeforeEach
    public void setUp() {
        Image avatar = Image.builder()
                .id(1L)
                .fileSize(1024)
                .mediaType("image/png")
                .data(new byte[]{1, 2, 3, 4})
                .build();
        testUser = new User();
        testUser.setAvatar(avatar);
        testUser.setId(1L);
        testUser.setUsername("test");

        LocalDateTime testDate = LocalDateTime.of(2024, 1, 1, 1, 1);

        testComment = new Comment();
        testComment.setId(1L);
        testComment.setText("testtesttest");
        testComment.setUser(testUser);
        testComment.setCreatedAt(testDate);
    }

    @Test
    void testMappingCommentToCommentResponse() {
        CommentResponse expected = new CommentResponse();
        expected.setPk(1);
        expected.setText("testtesttest");
        expected.setAuthor(testUser.getId().intValue());
        expected.setCreatedAt(1704060060000L);

        CommentResponse actual = commentMapper.toCommentResponse(testComment);

        assertThat(actual.getPk()).isEqualTo(expected.getPk());
        assertThat(actual.getText()).isEqualTo(expected.getText());
        assertThat(actual.getAuthor()).isEqualTo(expected.getAuthor());
        assertThat(actual.getCreatedAt()).isEqualTo(expected.getCreatedAt());
    }

    @Test
    void testMappingCommentToCreateOrUpdateComment() {
        CreateOrUpdateComment expected = new CreateOrUpdateComment("testtesttest");

        CreateOrUpdateComment actual = commentMapper.toCreateOrUpdateComment(testComment);

        assertThat(actual.getText()).isEqualTo(expected.getText());
    }

    @Test
    void testMappingListWithDto() {
        List<Comment> base = List.of(testComment);
        CommentResponse commentResponseTest = new CommentResponse();
        commentResponseTest.setPk(1);
        commentResponseTest.setText("testtesttest");
        commentResponseTest.setAuthor(testUser.getId().intValue());
        commentResponseTest.setCreatedAt(1704060060000L);
        commentResponseTest.setAuthorImage("/image/" + testUser.getAvatar().getId());
        List<CommentResponse> expected = List.of(commentResponseTest);

        List<CommentResponse> actual = commentMapper.toListWithDto(base);

        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testMappingCommentsResponse() {
        CommentResponse commentResponseTest = new CommentResponse();
        commentResponseTest.setPk(1);
        commentResponseTest.setText("testtesttest");
        commentResponseTest.setAuthor(testUser.getId().intValue());
        commentResponseTest.setCreatedAt(1704060060000L);
        commentResponseTest.setAuthorImage("/image/" + testUser.getAvatar().getId());
        List<Comment> base = List.of(testComment);
        List<CommentResponse> second = List.of(commentResponseTest);
        int countExpected = 1;
        CommentsResponse expected = new CommentsResponse();
        expected.setResults(second);
        expected.setCount(second.size());

        CommentsResponse actual = commentMapper.toCommentsResponse(base);

        assertThat(base.size()).isEqualTo(countExpected);
        assertThat(actual).isEqualTo(expected);
    }
}
