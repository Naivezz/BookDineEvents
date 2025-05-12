package com.naivez.mapper.event;

import com.naivez.dto.event.EventCreateEditDto;
import com.naivez.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventCreateEditMapper {

    @Mapping(source = "restaurantId", target = "restaurant.id")
    @Mapping(source = "time", target = "time")
    Event toEntity(EventCreateEditDto eventCreateEditDto);

    @Mapping(source = "restaurantId", target = "restaurant.id")
    @Mapping(source = "time", target = "time")
    void toEntity(EventCreateEditDto eventCreateEditDto, @MappingTarget Event event);
}
