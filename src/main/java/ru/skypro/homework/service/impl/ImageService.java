package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.StorageService;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${path.to.image}")
    private String imagesDir;

    private final ImageRepository imageRepository;

    private final StorageService fileSystemStorageService;

    /**
     * Сохранить куртинку
     * @param user пользователь которого надо сохранить {@link Image}
     * @return {@link Image}
     */
    public Image save(Image user) {
        return imageRepository.save(user);
    }

}
