package com.naivez.mapper.user;

import com.naivez.dto.user.UserCreateEditDto;
import com.naivez.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Mapper(componentModel = "spring")
public interface UserCreateEditMapper {

    User toEntity(UserCreateEditDto userCreateEditDto);

    void toEntity(UserCreateEditDto dto, @MappingTarget User entity);

    default void mapPassword(UserCreateEditDto dto, @MappingTarget User entity, PasswordEncoder passwordEncoder) {
        if (StringUtils.hasText(dto.getPassword())) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            entity.setPassword(encodedPassword);
        }
    }
}

