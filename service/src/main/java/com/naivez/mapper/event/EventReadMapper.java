package com.naivez.mapper.event;

import com.naivez.dto.event.EventReadDto;
import com.naivez.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventReadMapper {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.name", target = "restaurantName")
    EventReadDto toReadDto(Event event);
}
