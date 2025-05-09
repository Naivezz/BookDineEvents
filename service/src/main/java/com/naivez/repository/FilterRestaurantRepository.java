package com.naivez.repository;

import com.naivez.dto.restaurant.RestaurantFilter;
import com.naivez.entity.Restaurant;

import java.util.List;

public interface FilterRestaurantRepository {

    List<Restaurant> findAllByFilter(RestaurantFilter filter);
}
