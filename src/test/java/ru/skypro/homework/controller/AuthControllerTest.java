package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
        //registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        //registry.add("spring.liquibase.enabled", () -> "true"); // потом можно с liquibase попробовать
    }

    @Nested
    class RegisterTest {
        @Test
        @SneakyThrows
        void register_RegisterNewUser() {
            Register register = Register.builder()
                    .role(Role.USER)
                    .phone("+7 (111) 111-22-33")
                    .firstName("firstName")
                    .lastName("lastName")
                    .password("password")
                    .username("testuser@gmail.com")
                    .build();
            String jsonRegister = objectMapper.writeValueAsString(register);

            mockMvc.perform(
                            post("/register")
                                    .content(jsonRegister)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated());

            //todo как убедиться что пользователь действительно сохранен
        }

        @Test
        @SneakyThrows
        void register_UserAlreadyExists() {
            Register register = Register.builder()
                    .role(Role.USER)
                    .phone("+7 (111) 111-22-33")
                    .firstName("firstName")
                    .lastName("lastName")
                    .password("password")
                    .username("user@gmail.com")
                    .build();
            String jsonRegister = objectMapper.writeValueAsString(register);

            mockMvc.perform(
                            post("/register")
                                    .content(jsonRegister)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest());
        }

        @Test
        @SneakyThrows
        void register_ValidError() {
            Register register = Register.builder().build();
            String jsonRegister = objectMapper.writeValueAsString(register);

            MvcResult mvcResult = mockMvc.perform(
                            post("/register")
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
        void login() {
            Login login = Login.builder()
                    .password("password")
                    .username("user@gmail.com")
                    .build();
            String jsonLogin = objectMapper.writeValueAsString(login);

            mockMvc.perform(
                            post("/login")
                                    .content(jsonLogin)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        void login_IncorrectPassword() {
            Login login = Login.builder()
                    .password("IncorrectPassword")
                    .username("user@gmail.com")
                    .build();
            String jsonLogin = objectMapper.writeValueAsString(login);

            mockMvc.perform(
                            post("/login")
                                    .content(jsonLogin)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @SneakyThrows
        void login_IncorrectUser() {
            Login login = Login.builder()
                    .password("password")
                    .username("incorrect@gmail.com")
                    .build();
            String jsonLogin = objectMapper.writeValueAsString(login);

            mockMvc.perform(
                            post("/login")
                                    .content(jsonLogin)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @SneakyThrows
        void getRegister_validError() {
            Login login = Login.builder().build();
            String jsonLogin = objectMapper.writeValueAsString(login);

            MvcResult mvcResult = mockMvc.perform(
                            post("/login")
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