package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.skypro.homework.dto.response.AdResponse;
import ru.skypro.homework.dto.response.ExtendedAdResponse;
import ru.skypro.homework.model.Ad;

@Mapper(componentModel = "spring")
public interface AdMapper {
    @Mappings({
            @Mapping(target = "pk", source = "id"),
            @Mapping(target = "author", source = ""),
            @Mapping(target = "image", source = "model.ad.image"),
            @Mapping(target = "price", source = "model.ad.price"),
            @Mapping(target = "title", source = "model.ad.title"),
    })
    AdResponse toAdResponse(Ad ad);
    @Mappings({
            @Mapping(target = "pk", source = "model.ad.pk"),
            @Mapping(target = "author", source = ""),
            @Mapping(target = "image", source = "model.ad.image"),
            @Mapping(target = "price", source = "model.ad.price"),
            @Mapping(target = "title", source = "model.ad.title"),
    })
    Ad toAd(AdResponse adResponse);

    @Mappings({
            @Mapping(target = "pk", source = "model.ad.pk"),
            @Mapping(target = "authorFirstName", source = "authorFirstName"),
            @Mapping(target = "authorLastName", source = "model.ad.authorLastName"),
            @Mapping(target = "name", source = "model.ad.name"),
            @Mapping(target = "email", source = "model.ad.email"),
            @Mapping(target = "phone", source = "model.ad.phone"),
            @Mapping(target = "image", source = "model.ad.image"),
            @Mapping(target = "price", source = "model.ad.price"),
            @Mapping(target = "title", source = "model.ad.title"),
    })
    ExtendedAdResponse toExtendedAdResponse(Ad ad);
    @Mappings({
            @Mapping(target = "pk", source = "model.ad.pk"),
            @Mapping(target = "authorFirstName", source = "model.ad.authorFirstName"),
            @Mapping(target = "authorLastName", source = "model.ad.authorLastName"),
            @Mapping(target = "name", source = "model.ad.name"),
            @Mapping(target = "email", source = "model.ad.email"),
            @Mapping(target = "phone", source = "model.ad.phone"),
            @Mapping(target = "image", source = "model.ad.image"),
            @Mapping(target = "price", source = "model.ad.price"),
            @Mapping(target = "title", source = "model.ad.title"),
    })
    Ad toAd (  ExtendedAdResponse extendedAdResponse);


}
