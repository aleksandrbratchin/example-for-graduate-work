package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.response.AdResponse;
import ru.skypro.homework.model.Ad;

@Service
public class AdMapper {
    @Value("${download.url}")
    private String downloadUrl;

    public AdResponse mappingToDTO(Ad model) {
        AdResponse adResponse = new AdResponse();
        adResponse.setPk(Math.toIntExact(model.getId()));
        adResponse.setAuthor(Math.toIntExact(model.getUser().getId()));
        adResponse.setPrice(model.getPrice());
        adResponse.setTitle(model.getTitle());
        adResponse.setImage(String.format(downloadUrl, model.getAvatar().getId()));
        return adResponse;
    }

    public Ad mappingToModel(AdResponse dto) {
        Ad ad = new Ad();
        ad.setId(Long.valueOf(dto.getPk()));
        ad.setPrice(dto.getPrice());
        ad.setTitle(dto.getTitle());
        return ad;
    }
}
