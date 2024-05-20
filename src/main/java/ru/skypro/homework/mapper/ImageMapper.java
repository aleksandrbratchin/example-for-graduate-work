package ru.skypro.homework.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;

import java.io.IOException;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {

    @Mapping(target = "data", expression = "java(mapMultipartFileToByteArray(file))")
    @Mapping(target = "id", ignore = true)
    public abstract Image toImage(MultipartFile file);

    protected byte[] mapMultipartFileToByteArray(MultipartFile file) {
        try {
            return file != null ? file.getBytes() : null;
        } catch (IOException e) {
            // Handle the exception as per your requirement
            throw new RuntimeException("Error converting MultipartFile to byte array", e);
        }
    }
}
