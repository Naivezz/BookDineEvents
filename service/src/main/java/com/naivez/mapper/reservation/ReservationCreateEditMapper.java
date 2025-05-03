package com.naivez.mapper.reservation;

import com.naivez.dto.reservation.ReservationCreateEditDto;
import com.naivez.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReservationCreateEditMapper {

    @Mapping(target = "restaurant.id", source = "restaurantId")
    Reservation toEntity(ReservationCreateEditDto reservationCreateEditDto);

    @Mapping(target = "restaurant.id", source = "restaurantId")
    void toEntity(ReservationCreateEditDto reservationCreateEditDto, @MappingTarget Reservation reservation);
}
