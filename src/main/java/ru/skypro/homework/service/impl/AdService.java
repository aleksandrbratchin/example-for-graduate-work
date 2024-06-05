package ru.skypro.homework.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdServiceApi;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService implements AdServiceApi {

    private final AdRepository adRepository;

    @Override
    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    @Override
    @Transactional
    public Ad createAdWithImage(Ad ad, Image image) {
        ad.setImage(image);
        return adRepository.save(ad);
    }

    @Override
    public Ad getAdById(Long id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Такого объявления не найдено"));
    }

    @Override
    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }

    @Override
    public Ad updateAdDetails(Long id, CreateOrUpdateAd properties) {
        Ad ad = getAdById(id);
        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setDescription(properties.getDescription());
        return adRepository.save(ad);
    }

    @Override
    public List<Ad> getUserAds(User user) {
        return adRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Ad updateAdImage(Long id, Image image) {
        Ad ad = getAdById(id);
        ad.setImage(image);
        return adRepository.save(ad);
    }

    @Override
    public Ad save(Ad ad) {
        return adRepository.save(ad);
    }

}
