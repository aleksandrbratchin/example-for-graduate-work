package ru.skypro.homework.service;

import jakarta.transaction.Transactional;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

import java.util.List;

public interface AdServiceApi {
    List<Ad> getAllAds();

    @Transactional
    Ad createAd(Ad ad, Image image);

    Ad findById(Long id);

    void deleteAd(Long id);

    Ad updateAd(Long id, CreateOrUpdateAd properties);

    List<Ad> getAdsByUser(User user);

    @Transactional
    Ad updateImageAd(Long id, Image image);
}
