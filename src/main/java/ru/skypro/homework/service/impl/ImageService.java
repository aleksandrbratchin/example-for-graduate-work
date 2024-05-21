package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    /**
     * Получить картинку по id
     * @param id картинки
     * @return {@link Image}
     */
    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException()); //todo exception
    }

}
