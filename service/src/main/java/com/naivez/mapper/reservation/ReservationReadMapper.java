package com.naivez.mapper.reservation;

import com.naivez.dto.reservation.ReservationReadDto;
import com.naivez.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationReadMapper {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    ReservationReadDto toDto(Reservation reservation);
}
