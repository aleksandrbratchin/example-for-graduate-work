package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.model.Ad;

@Service
public class CreateOrUpdateAdMapper {

    public CreateOrUpdateAd mapToDto(Ad model){
        CreateOrUpdateAd dto = new CreateOrUpdateAd();
        dto.setTitle(model.getTitle());
        dto.setPrice(model.getPrice());
        dto.setDescription(model.getDescription());
        return dto;
    }

    public Ad mapToEntity(CreateOrUpdateAd dto){
        Ad model = new Ad();
        model.setTitle(dto.getTitle());
        model.setPrice(dto.getPrice());
        model.setDescription(dto.getDescription());
        return model;
    }
}
