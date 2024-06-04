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

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService implements ru.skypro.homework.service.AdServiceApi {

    private final AdRepository adRepository;

    @Override
    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    @Override
    @Transactional
    public Ad createAd(Ad ad, Image image) {
        ad.setImage(image);
        return adRepository.save(ad);
    }

    @Override
    public Ad findById(Long id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Такого объявления не найдено"));
    }

    @Override
    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }

    @Override
    public Ad updateAd(Long id, CreateOrUpdateAd properties) {
        Ad ad = findById(id);
        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setDescription(properties.getDescription());
        return adRepository.save(ad);
    }

    @Override
    public List<Ad> getAdsByUser(User user) {
        return adRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Ad updateImageAd(Long id, Image image) {
        Ad ad = findById(id);
        ad.setImage(image);
        return adRepository.save(ad);
    }

}
