package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("integration-tests-db")
            .withInitScript("scriptdb/createdb.sql");
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void getComments() {
        mockMvc.perform(get("/ads/2/comments"))
                .andExpect((status().is2xxSuccessful()));
    }

    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void addComment() {
        CreateOrUpdateComment createOrUpdateComment=new CreateOrUpdateComment("новый комментарий");
        mockMvc.perform(post("/ads/2/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void deleteCommentByUserWhoCreate() {
        mockMvc.perform(delete("/ads/2/comments/2"))
                .andExpect((status().is2xxSuccessful()));

    }
    @Test
    @SneakyThrows
    @WithUserDetails("james.norrington@gmail.com")
    void deleteCommentBySomeoneElse() {
        mockMvc.perform(delete("/ads/2/comments/2"))
                .andExpect((status().is4xxClientError()));

    }

    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void updateCommentByUserWhoCreate() {
        CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("обновленный комментарий");

        mockMvc.perform(patch("/ads/2/comments/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("обновленный комментарий"));

    }
    @Test
    @SneakyThrows
    @WithUserDetails("james.norrington@gmail.com")
    void updateCommentBySomeoneElse() {
        CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment("обновленный комментарий");

        mockMvc.perform(patch("/ads/2/comments/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrUpdateComment)))
                .andExpect(status().is4xxClientError());
    }
}