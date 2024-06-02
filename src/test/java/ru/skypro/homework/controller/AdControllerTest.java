package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
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
import ru.skypro.homework.dto.CreateOrUpdateAd;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("integration-tests-db");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.contexts", () -> "prod,test");
    }

    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void getAllAds() {
        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void addAd() {
    }

    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void getAdById() {
        mockMvc.perform(get("/ads/1"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void deleteAd() {
        mockMvc.perform(delete("/ads/1"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void updateAdInfo() {
        CreateOrUpdateAd ad = new CreateOrUpdateAd("testadeaddde", 100, "testtestadad");
        mockMvc.perform(patch("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ad)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(ad.getTitle()))
                .andExpect(jsonPath("$.price").value(ad.getPrice()));
    }

    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void getAdsByAuthUser() {
        mockMvc.perform(get("/ads/me"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("captain.jack.sparrow@gmail.com")
    void updateImageAds(){
    }
}
