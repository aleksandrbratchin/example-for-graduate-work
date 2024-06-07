package ru.skypro.homework.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTest {

    private static final String USER_JACK = "captain.jack.sparrow@gmail.com";
    private static final String IMAGE_URL_TEMPLATE = "/image/{id}";
    private static final int EXISTING_IMAGE_ID = 2;
    private static final int NON_EXISTING_IMAGE_ID = 9999;
    private static final String EXPECTED_CONTENT_TYPE = MediaType.IMAGE_JPEG_VALUE;
    private static final int EXPECTED_CONTENT_LENGTH = 135510;

    @Autowired
    private MockMvc mockMvc;

    @Container
    @ServiceConnection
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.3-bullseye"))
            .withDatabaseName("integration-tests-db");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.contexts", () -> "prod,test");
    }
    @Test
    @SneakyThrows
    @WithUserDetails(USER_JACK)
    void shouldReturnImageWhenImageExists() {
        mockMvc.perform(get(IMAGE_URL_TEMPLATE, EXISTING_IMAGE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
                .andExpect(header().string(HttpHeaders.CONTENT_LENGTH, String.valueOf(EXPECTED_CONTENT_LENGTH)));
    }

    @Test
    @SneakyThrows
    @WithUserDetails(USER_JACK)
    void shouldReturnNotFoundWhenImageDoesNotExist() {
        mockMvc.perform(get(IMAGE_URL_TEMPLATE, NON_EXISTING_IMAGE_ID))
                .andExpect(status().isNotFound());
    }
}