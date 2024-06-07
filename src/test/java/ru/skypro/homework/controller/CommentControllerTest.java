package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.skypro.homework.dto.CreateOrUpdateComment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    private static final String BASE_URL = "/ads";
    private static final String COMMENT_URL = BASE_URL + "/{adId}/comments";
    private static final String USER_JACK = "captain.jack.sparrow@gmail.com";
    private static final String USER_ELIZABETH = "elizabeth.swann@gmail.com";
    private static final String USER_HECTOR = "hector.barbossa@gmail.com";
    private static final String USER_JAMES = "james.norrington@gmail.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    @ServiceConnection
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.3-bullseye"))
            .withDatabaseName("integration-tests-db");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.contexts", () -> "prod,test");
    }

    @Nested
    class GetCommentTests {
        @Test
        @SneakyThrows
        @WithUserDetails(USER_JACK)
        void shouldGetComments() {
            mockMvc.perform(get(COMMENT_URL, 2))
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        void shouldReturnUnauthorizedByUnauthorizedUser() {
            mockMvc.perform(get(COMMENT_URL, 2))
                    .andExpect(status().isUnauthorized());
        }
    }


    @Nested
    class AddCommentTests {
        @Test
        @SneakyThrows
        @WithUserDetails(USER_JACK)
        void shouldAddComment() {
            CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("новый комментарий");
            mockMvc.perform(post(COMMENT_URL, 2)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        void shouldReturnUnauthorizedForAddingComment() {
            CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("попытка написать комментарий");

            mockMvc.perform(post(COMMENT_URL, 2)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class DeleteCommentTests {

        @Test
        @SneakyThrows
        @WithUserDetails(USER_JACK)
        void shouldDeleteCommentByCreator() {
            mockMvc.perform(delete(COMMENT_URL + "/{commentId}", 1, 1))
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        @WithUserDetails(USER_ELIZABETH)
        void shouldReturnForbiddenForDeletingCommentByAnotherUser() {
            mockMvc.perform(delete(COMMENT_URL + "/{commentId}", 1, 3))
                    .andExpect(status().isForbidden());
        }

        @Test
        @SneakyThrows
        @WithUserDetails(USER_HECTOR)
        void shouldDeleteCommentByAdmin() {
            mockMvc.perform(delete(COMMENT_URL + "/{commentId}", 2, 2))
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        void shouldReturnUnauthorizedDeletingCommentByUnauthorizedUser() {
            mockMvc.perform(delete(COMMENT_URL + "/{commentId}", 1, 3))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class UpdateCommentTests {

        @Test
        @SneakyThrows
        @WithUserDetails(USER_JACK)
        void shouldUpdateCommentByCreator() {
            CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("обновленный комментарий");

            mockMvc.perform(patch(COMMENT_URL + "/{commentId}", 2, 2)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text").value("обновленный комментарий"));
        }

        @Test
        @SneakyThrows
        @WithUserDetails(USER_ELIZABETH)
        void shouldReturnForbiddenForUpdatingCommentByAnotherUser() {
            CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("обновленный комментарий");

            mockMvc.perform(patch(COMMENT_URL + "/{commentId}", 1, 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                    .andExpect(status().isForbidden());
        }

        @Test
        @SneakyThrows
        @WithUserDetails(USER_JAMES)
        void shouldUpdateCommentByAdmin() {
            CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("обновленный комментарий админом");

            mockMvc.perform(patch(COMMENT_URL + "/{commentId}", 2, 2)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        void shouldReturnUnauthorizedUpdateCommentByUnauthorizedUser() {
            CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("обновленный комментарий админом");

            mockMvc.perform(patch(COMMENT_URL + "/{commentId}", 2, 2)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class NotFoundErrorTests {

        @Test
        @SneakyThrows
        @WithUserDetails(USER_JAMES)
        void shouldReturnNotFoundForNonexistentAd() {
            CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("новый комментарий");

            mockMvc.perform(post(COMMENT_URL, 200)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                    .andExpect(status().isNotFound());
        }

        @Test
        @SneakyThrows
        @WithUserDetails(USER_JAMES)
        void shouldReturnNotFoundForNonexistentComment() {
            CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("новый комментарий");

            mockMvc.perform(patch(COMMENT_URL + "/{commentId}", 1, 200)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class ValidationErrorTests {

        @Test
        @SneakyThrows
        @WithUserDetails(USER_JACK)
        void shouldReturnForbiddenForEmptyCommentTextOnUpdate() {
            CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("");

            mockMvc.perform(patch(COMMENT_URL + "/{commentId}", 2, 2)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                    .andExpect(status().isForbidden());
        }

        @Test
        @SneakyThrows
        @WithUserDetails(USER_JACK)
        void shouldReturnUnauthorizedForEmptyCommentTextOnAdd() {
            CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("");
            mockMvc.perform(post(COMMENT_URL, 2)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                    .andExpect(status().isUnauthorized());
        }
    }
}
