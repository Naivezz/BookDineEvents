package com.naivez.repository;

import com.naivez.dto.restaurant.RestaurantFilter;
import com.naivez.entity.Restaurant;
import com.naivez.repository.predicate.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.naivez.entity.QRestaurant.restaurant;

@RequiredArgsConstructor
public class FilterRestaurantRepositoryImpl implements FilterRestaurantRepository {

    private final EntityManager entityManager;

    @Override
    public List<Restaurant> findAllByFilter(RestaurantFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getName(), restaurant.name::containsIgnoreCase)
                .add(filter.getAddress(), restaurant.address::containsIgnoreCase)
                .buildOr();

        return new JPAQuery<Restaurant>(entityManager)
                .select(restaurant)
                .from(restaurant)
                .where(predicate)
                .fetch();
    }
}
