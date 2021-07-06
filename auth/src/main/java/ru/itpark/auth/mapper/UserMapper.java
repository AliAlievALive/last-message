package ru.itpark.auth.mapper;

import org.mapstruct.Mapper;
import ru.itpark.auth.dto.User;
import ru.itpark.auth.entity.UserEntity;

@Mapper
public interface UserMapper {

    User toDto(UserEntity entity);

    UserEntity toEntity(User dto);

}
