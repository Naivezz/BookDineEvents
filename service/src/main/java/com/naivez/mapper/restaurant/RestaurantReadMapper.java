package com.naivez.mapper.restaurant;

import com.naivez.dto.restaurant.RestaurantReadDto;
import com.naivez.entity.Restaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantReadMapper {

    RestaurantReadDto toDto(Restaurant restaurant);
}
