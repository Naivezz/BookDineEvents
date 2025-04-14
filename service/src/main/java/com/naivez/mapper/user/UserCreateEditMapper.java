package com.naivez.mapper.user;

import com.naivez.dto.user.UserCreateEditDto;
import com.naivez.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserCreateEditMapper {

    User toEntity(UserCreateEditDto userCreateEditDto);

    UserCreateEditDto toDto(User user);

    void toEntity(UserCreateEditDto dto, @MappingTarget User entity);
}

