package com.naivez.mapper.user;

import com.naivez.dto.user.UserReadDto;
import com.naivez.entity.User;
import com.naivez.mapper.reservation.ReservationReadMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserReadMapper {

    UserReadDto toDto(User user);
}
