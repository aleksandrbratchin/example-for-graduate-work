package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.response.AdResponse;
import ru.skypro.homework.dto.response.AdsResponse;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;

//@Mapper(componentModel = "spring")
@Component
public class AdsMapper {
    @Autowired
    private AdMapper adMapper;

    //    @Mapping(target = "count", expression = "java(ads.size())")
//    @Mapping(target = "results", expression = "java(fromAds(ads))")
    public AdsResponse toAdsResponse(List<Ad> ads) {
        AdsResponse adsResponse = new AdsResponse();
        adsResponse.setCount(ads.size());
        adsResponse.setResults(fromAds(ads));
        return adsResponse;
    }


    protected List<AdResponse> fromAds(List<Ad> ads) {
        List<AdResponse> result = ads
                .stream()
                .map(adMapper::mappingToDto)
                .toList();
        return result;
    }
}
