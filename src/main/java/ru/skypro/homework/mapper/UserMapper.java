package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatar", ignore = true)
    User fromRegister(Register register);

}
