package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.response.AdResponse;
import ru.skypro.homework.model.Ad;

@Mapper(componentModel = "spring")
public interface CreateOrUpdateAdMapper {

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "id", ignore = true)
    Ad toAd(CreateOrUpdateAd createOrUpdateAd);

//    public CreateOrUpdateAd mapToDto(Ad model){
//        CreateOrUpdateAd dto = new CreateOrUpdateAd();
//        dto.setTitle(model.getTitle());
//        dto.setPrice(model.getPrice());
//        dto.setDescription(model.getDescription());
//        return dto;
//    }
//
//    public Ad mapToEntity(CreateOrUpdateAd dto){
//        Ad model = new Ad();
//        model.setTitle(dto.getTitle());
//        model.setPrice(dto.getPrice());
//        model.setDescription(dto.getDescription());
//        return model;
//    }
}
