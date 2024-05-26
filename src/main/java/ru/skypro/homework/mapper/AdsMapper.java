package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.response.AdResponse;
import ru.skypro.homework.dto.response.AdsResponse;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;
import java.util.stream.Collectors;

//@Mapper(componentModel = "spring")
@Component
public class AdsMapper {

    private final AdMapper adMapper;
    private final AdRepository adRepository;

    @Autowired
    public AdsMapper(AdMapper adMapper, AdRepository adRepository) {
        this.adMapper = adMapper;
        this.adRepository = adRepository;
    }

    public AdsResponse toAdsResponse() {
        List<AdResponse> result = adRepository.findAll()
                .stream()
                .map(ad -> adMapper.mappingToDto(ad))
                .collect(Collectors.toList());
        AdsResponse adsResponse = new AdsResponse();
        adsResponse.setCount(result.size());
        adsResponse.setResults(result);
        return adsResponse;
    }
    //    @Mapping(target = "count", expression = "java(ads.size())")
//    @Mapping(target = "results", expression = "java(fromAds(ads))")
//    public AdsResponse toAdsResponse(List<Ad> ads) {
//        AdsResponse adsResponse = new AdsResponse();
//        adsResponse.setCount(ads.size());
//        adsResponse.setResults(fromAds(ads));
//        return adsResponse;
//    }
//
//
//    protected List<AdResponse> fromAds(List<Ad> ads) {
//        List<AdResponse> result = ads
//                .stream()
//                .map(adMapper::mappingToDto)
//                .toList();
//        return result;
//    }
}
