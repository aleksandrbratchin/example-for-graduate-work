package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.model.Ad;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CreateOrUpdateAdMapperTest {

    private final CreateOrUpdateAdMapper createOrUpdateAdMapper;

    @Autowired
    public CreateOrUpdateAdMapperTest(CreateOrUpdateAdMapper createOrUpdateAdMapper) {
        this.createOrUpdateAdMapper = createOrUpdateAdMapper;
    }

    @Test
    void shouldMapCreateOrUpdateAdToAd() {
        Ad originalAd = new Ad();
        originalAd.setId(3L);
        originalAd.setPrice(100);
        originalAd.setTitle("test");
        originalAd.setDescription("test");

        CreateOrUpdateAd createOrUpdateAdDto = new CreateOrUpdateAd();
        createOrUpdateAdDto.setPrice(100);
        createOrUpdateAdDto.setTitle("test");
        createOrUpdateAdDto.setDescription("test");

        Ad mappedAd = createOrUpdateAdMapper.toAd(createOrUpdateAdDto);

        assertThat(mappedAd).isNotNull();
        assertThat(mappedAd.getPrice()).isEqualTo(createOrUpdateAdDto.getPrice());
        assertThat(mappedAd.getTitle()).isEqualTo(createOrUpdateAdDto.getTitle());
        assertThat(mappedAd.getDescription()).isEqualTo(createOrUpdateAdDto.getDescription());
    }
}
