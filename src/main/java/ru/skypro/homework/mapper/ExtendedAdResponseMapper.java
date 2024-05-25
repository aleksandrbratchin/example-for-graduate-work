package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.response.ExtendedAdResponse;
import ru.skypro.homework.model.Ad;

@Service
public class ExtendedAdResponseMapper {
    @Value("${download.url}")
    private String downloadUrl;

    public ExtendedAdResponse mapToDto(Ad model) {
        ExtendedAdResponse dto = new ExtendedAdResponse();
        dto.setPk(Math.toIntExact(model.getId()));
        dto.setAuthorFirstName(model.getUser().getFirstName());
        dto.setAuthorLastName(model.getUser().getLastName());
        dto.setDescription(model.getDescription());
        dto.setEmail(model.getUser().getUsername());
        dto.setImage(String.format(downloadUrl, model.getAvatar().getId()));
        dto.setPhone(model.getUser().getPhone());
        dto.setPrice(model.getPrice());
        dto.setTitle(model.getTitle());
        return dto;
    }
}
