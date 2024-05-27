package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.response.UserResponse;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testToUserResponse() {
        Image avatar = Image.builder()
                .id(1L)
                .fileSize(1024)
                .mediaType("image/png")
                .data(new byte[]{1, 2, 3, 4})
                .build();
        User user = User.builder()
                .id(2L)
                .username("testuser")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .phone("+7 (123) 456-78-99")
                .role(Role.USER)
                .avatar(avatar)
                .build();

        UserResponse userResponse = userMapper.toUserResponse(user);

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getId()).isEqualTo(2L);
        assertThat(userResponse.getUsername()).isEqualTo("testuser");
        assertThat(userResponse.getPassword()).isEqualTo("password");
        assertThat(userResponse.getFirstName()).isEqualTo("John");
        assertThat(userResponse.getLastName()).isEqualTo("Doe");
        assertThat(userResponse.getPhone()).isEqualTo("+7 (123) 456-78-99");
        assertThat(userResponse.getRole()).isEqualTo(Role.USER);
        assertThat(userResponse.getImage()).isEqualTo("/image/" + avatar.getId());
    }

}