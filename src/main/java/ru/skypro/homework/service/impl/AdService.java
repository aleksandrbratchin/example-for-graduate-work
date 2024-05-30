package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.response.AdResponse;
import ru.skypro.homework.dto.response.AdsResponse;
import ru.skypro.homework.dto.response.ExtendedAdResponse;
import ru.skypro.homework.mapper.*;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdService {

    private final AdMapper adMapper;
    private final AdsMapper adsMapper;
    private final CreateOrUpdateAdMapper createOrUpdateAdMapper;
    private final ExtendedAdResponseMapper extendedAdResponseMapper;
    private final AdRepository adRepository;
    private final ImageMapper imageMapper;
    private final UserRepository userRepository;

    @Autowired
    public AdService(AdMapper adMapper, AdsMapper adsMapper, CreateOrUpdateAdMapper createOrUpdateAdMapper, ExtendedAdResponseMapper extendedAdResponseMapper, AdRepository adRepository, ImageMapper imageMapper, UserRepository userRepository) {
        this.adMapper = adMapper;
        this.adsMapper = adsMapper;
        this.createOrUpdateAdMapper = createOrUpdateAdMapper;
        this.extendedAdResponseMapper = extendedAdResponseMapper;
        this.adRepository = adRepository;
        this.imageMapper = imageMapper;
        this.userRepository = userRepository;
    }

    public AdsResponse getAllAds() {
        return adsMapper.toAdsResponse();
    }

    public AdResponse createAd(CreateOrUpdateAd properties, MultipartFile image, User user) {
        Ad ad = createOrUpdateAdMapper.toAd(properties);
        Image image1 = imageMapper.toImage(image);
        ad.setImage(image1);
        ad.setUser(user);
        Ad save = adRepository.save(ad);
        return adMapper.mappingToDto(save);
    }

    public ExtendedAdResponse getAdById(Long id) {
        Ad ad = adRepository.findById(id).orElseThrow(RuntimeException::new); //todo
        return extendedAdResponseMapper.toDto(ad);
    }

    public void deleteAd(long id) {
        adRepository.deleteById(id);
    }

    public AdResponse updateAd(Long id, CreateOrUpdateAd properties) {
        Ad ad = adRepository.findById(id).orElseThrow(RuntimeException::new);
        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setDescription(properties.getDescription());
        Ad save = adRepository.save(ad);
        return adMapper.mappingToDto(save);
    }

    public AdsResponse getAdsByAuthUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        List<AdResponse> result = adRepository.findByUser(user)
                .stream()
                .map(adMapper::mappingToDto)
                .collect(Collectors.toList());
        AdsResponse adsResponse = new AdsResponse();
        adsResponse.setCount(result.size());
        adsResponse.setResults(result);
        return adsResponse;
    }

    public AdResponse updateImageAd(Long id, MultipartFile image) {
        Ad ad = adRepository.findById(id).orElseThrow(RuntimeException::new);
        Image image1 = imageMapper.toImage(image);
        ad.setImage(image1);
        Ad save = adRepository.save(ad);
        return adMapper.mappingToDto(save);
    }
}
