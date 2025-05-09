package com.naivez.repository;

import com.naivez.entity.Restaurant;
import com.naivez.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, QuerydslPredicateExecutor<Restaurant>, FilterRestaurantRepository {
}
