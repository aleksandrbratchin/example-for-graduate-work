package ru.skypro.homework.jackson.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.Role;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleUserDeserializerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Role.class, new RoleUserDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Deserialize USER role")
    public void testDeserializeUser() throws IOException {
        String json = "\"USER\"";
        Role role = objectMapper.readValue(json, Role.class);
        assertThat(role).isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("Deserialize ADMIN role")
    public void testDeserializeAdmin() throws IOException {
        String json = "\"ADMIN\"";
        Role role = objectMapper.readValue(json, Role.class);
        assertThat(role).isEqualTo(Role.ADMIN);
    }

    @Test
    @DisplayName("Deserialize invalid role returns null")
    public void testDeserializeInvalidRole() throws IOException {
        String json = "\"INVALID_ROLE\"";
        Role role = objectMapper.readValue(json, Role.class);
        assertThat(role).isNull();
    }
}
