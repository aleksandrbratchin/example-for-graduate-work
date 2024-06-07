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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private static final String USER_JACK = "captain.jack.sparrow@gmail.com";
    private static final String USER_DAVY = "davy.jones@gmail.com";
    private static final String REGISTER_URL = "/register";
    private static final String LOGIN_URL = "/login";

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
    class RegisterTest {

        @Test
        @SneakyThrows
        void shouldRegisterNewUser() {
            Register register = Register.builder()
                    .role(Role.ADMIN)
                    .phone("+7 (666) 6666666")
                    .firstName("Davy")
                    .lastName("Jones")
                    .password("Locker123")
                    .username(USER_DAVY)
                    .build();
            String jsonRegister = objectMapper.writeValueAsString(register);

            mockMvc.perform(
                            post(REGISTER_URL)
                                    .content(jsonRegister)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated());
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestForExistingUser() {
            Register register = Register.builder()
                    .role(Role.ADMIN)
                    .phone("+7 (812) 1234567")
                    .firstName("Jack")
                    .lastName("Sparrow")
                    .password("BlackPearl123")
                    .username(USER_JACK)
                    .build();
            String jsonRegister = objectMapper.writeValueAsString(register);

            mockMvc.perform(
                            post(REGISTER_URL)
                                    .content(jsonRegister)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest());
        }

        @Test
        @SneakyThrows
        void shouldReturnValidationErrorsForInvalidRegister() {
            Register register = Register.builder().build();
            String jsonRegister = objectMapper.writeValueAsString(register);

            MvcResult mvcResult = mockMvc.perform(
                            post(REGISTER_URL)
                                    .content(jsonRegister)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest())
                    .andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            assertThat(response.getContentAsString()).contains("Логин не может быть пуст");
            assertThat(response.getContentAsString()).contains("Неверная роль");
            assertThat(response.getContentAsString()).contains("Имя не может быть пустым");
            assertThat(response.getContentAsString()).contains("Номер телефона не соответствует формату");
            assertThat(response.getContentAsString()).contains("Фамилия не может быть пустой");
            assertThat(response.getContentAsString()).contains("Пароль не может быть пустым");
        }
    }

    @Nested
    class LoginTest {

        @Test
        @SneakyThrows
        void shouldLoginSuccessfully() {
            Login login = Login.builder()
                    .password("BlackPearl123")
                    .username(USER_JACK)
                    .build();
            String jsonLogin = objectMapper.writeValueAsString(login);

            mockMvc.perform(
                            post(LOGIN_URL)
                                    .content(jsonLogin)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        void shouldReturnUnauthorizedForIncorrectPassword() {
            Login login = Login.builder()
                    .password("WickedWench")
                    .username(USER_JACK)
                    .build();
            String jsonLogin = objectMapper.writeValueAsString(login);

            mockMvc.perform(
                            post(LOGIN_URL)
                                    .content(jsonLogin)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @SneakyThrows
        void shouldReturnUnauthorizedForIncorrectUser() {
            Login login = Login.builder()
                    .password("password")
                    .username("incorrect@gmail.com")
                    .build();
            String jsonLogin = objectMapper.writeValueAsString(login);

            mockMvc.perform(
                            post(LOGIN_URL)
                                    .content(jsonLogin)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @SneakyThrows
        void shouldReturnValidationErrorsForInvalidLogin() {
            Login login = Login.builder().build();
            String jsonLogin = objectMapper.writeValueAsString(login);

            MvcResult mvcResult = mockMvc.perform(
                            post(LOGIN_URL)
                                    .content(jsonLogin)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isUnauthorized())
                    .andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            assertThat(response.getContentAsString()).contains("Логин не может быть пуст");
            assertThat(response.getContentAsString()).contains("Пароль не может быть пустым");
        }
    }
}