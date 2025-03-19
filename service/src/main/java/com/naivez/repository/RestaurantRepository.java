package com.naivez.repository;

import com.naivez.entity.Restaurant;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepository extends RepositoryBase<Long, Restaurant>{
    public RestaurantRepository(EntityManager entityManager) {
        super(Restaurant.class, entityManager);
    }
}
