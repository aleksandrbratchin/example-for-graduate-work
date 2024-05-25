package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.response.AdResponse;
import ru.skypro.homework.dto.response.AdsResponse;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CreateOrUpdateAdMapper;
import ru.skypro.homework.mapper.ExtendedAdResponseMapper;
import ru.skypro.homework.repository.AdRepository;

@Service
public class AdService {

    private final AdMapper adMapper;
    private final AdsMapper adsMapper;
    private final CreateOrUpdateAdMapper createOrUpdateAdMapper;
    private final ExtendedAdResponseMapper extendedAdResponseMapper;
    private final AdRepository adRepository;

    @Autowired
    public AdService(AdMapper adMapper, AdsMapper adsMapper, CreateOrUpdateAdMapper createOrUpdateAdMapper, ExtendedAdResponseMapper extendedAdResponseMapper, AdRepository adRepository) {
        this.adMapper = adMapper;
        this.adsMapper = adsMapper;
        this.createOrUpdateAdMapper = createOrUpdateAdMapper;
        this.extendedAdResponseMapper = extendedAdResponseMapper;
        this.adRepository = adRepository;
    }

    public AdsResponse getAllAds() {
        return adsMapper.getAllAds();
    }

//public AdResponse addAd(CreateOrUpdateAd properties, MultipartFile image){
//
//}
}
