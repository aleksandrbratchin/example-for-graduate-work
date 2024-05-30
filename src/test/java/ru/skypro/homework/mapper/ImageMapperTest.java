package ru.skypro.homework.mapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.homework.model.Image;

import static org.assertj.core.api.Assertions.assertThat;

class ImageMapperTest {

    private final ImageMapper imageMapper = Mappers.getMapper(ImageMapper.class);

    @Test
    @SneakyThrows
    public void testToImage() {
        byte[] fileContent = {1, 2, 3, 4};
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test.png",
                "image/png",
                fileContent
        );

        Image image = imageMapper.toImage(multipartFile);

        assertThat(image).isNotNull();
        assertThat(image.getFileSize()).isEqualTo(multipartFile.getSize());
        assertThat(image.getMediaType()).isEqualTo(multipartFile.getContentType());
        assertThat(image.getData()).isEqualTo(multipartFile.getBytes());
        assertThat(image.getId()).isNull();
    }

    @Test
    public void testToImageWithNullFile() {
        Image image = imageMapper.toImage(null);

        assertThat(image).isNull();
    }

}