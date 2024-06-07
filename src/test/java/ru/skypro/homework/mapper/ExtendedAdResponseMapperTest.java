package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.response.ExtendedAdResponse;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ExtendedAdResponseMapperTest {

    private final ExtendedAdResponseMapper extendedAdResponseMapper;

    @Autowired
    public ExtendedAdResponseMapperTest(ExtendedAdResponseMapper extendedAdResponseMapper) {
        this.extendedAdResponseMapper = extendedAdResponseMapper;
    }

    @Test
    void shouldMapAdToExtendedAdResponse() {

        Image avatar = Image.builder()
                .id(1L)
                .fileSize(1024)
                .mediaType("image/png")
                .data(new byte[]{1, 2, 3, 4})
                .build();

        User user = User.builder()
                .id(2L)
                .username("test")
                .password("test")
                .firstName("test")
                .lastName("test")
                .phone("3456787654")
                .role(Role.USER)
                .avatar(avatar)
                .build();

        Ad ad = new Ad();
        ad.setId(3L);
        ad.setPrice(100);
        ad.setTitle("test");
        ad.setDescription("test");
        ad.setImage(avatar);
        ad.setUser(user);

        ExtendedAdResponse extendedAdResponse = extendedAdResponseMapper.toDto(ad);

        assertThat(extendedAdResponse).isNotNull();
        assertThat(extendedAdResponse.getPk()).isEqualTo(ad.getId());
        assertThat(extendedAdResponse.getAuthorFirstName()).isEqualTo(ad.getUser().getFirstName());
        assertThat(extendedAdResponse.getAuthorLastName()).isEqualTo(ad.getUser().getLastName());
        assertThat(extendedAdResponse.getImage()).isEqualTo("/image/" + avatar.getId());
        assertThat(extendedAdResponse.getEmail()).isEqualTo(ad.getUser().getUsername());
        assertThat(extendedAdResponse.getPrice()).isEqualTo(ad.getPrice());
        assertThat(extendedAdResponse.getPhone()).isEqualTo(ad.getUser().getPhone());
        assertThat(extendedAdResponse.getTitle()).isEqualTo(ad.getTitle());
        assertThat(extendedAdResponse.getDescription()).isEqualTo(ad.getDescription());
    }

}
