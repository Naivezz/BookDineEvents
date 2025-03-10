package com.naivez.dao;

import com.naivez.entity.Restaurant;
import jakarta.persistence.EntityManager;

public class RestaurantRepository extends RepositoryBase<Long, Restaurant>{

    public RestaurantRepository(Class<Restaurant> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
