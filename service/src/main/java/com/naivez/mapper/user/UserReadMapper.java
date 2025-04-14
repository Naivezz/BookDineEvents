package com.naivez.mapper.user;

import com.naivez.dto.user.UserReadDto;
import com.naivez.entity.User;
import com.naivez.mapper.reservation.ReservationReadMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ReservationReadMapper.class)
public interface UserReadMapper {

    UserReadDto toDto(User user);
}
