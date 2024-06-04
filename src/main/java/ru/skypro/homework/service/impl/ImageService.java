package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageServiceApi;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageServiceApi {

    private final ImageRepository imageRepository;

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException("Изображение с идентификатором " + id + " не найдено!")
        );
    }

}
