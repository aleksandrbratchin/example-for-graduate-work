package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.response.AdResponse;
import ru.skypro.homework.dto.response.AdsResponse;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;

@Service
public class AdsMapper {

    private final AdRepository adRepository;
    private final AdMapper adMapper;

    @Autowired
    public AdsMapper(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    public AdsResponse getAllAds() {
        List<AdResponse> result = adRepository.findAll()
                .stream()
                .map(adMapper::mappingToDTO)
                .toList();
        AdsResponse adsResponse = new AdsResponse();
        adsResponse.setCount(result.size());
        adsResponse.setResults(result);
        return adsResponse;
    }
}
