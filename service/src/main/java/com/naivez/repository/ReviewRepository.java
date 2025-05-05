package com.naivez.repository;

import com.naivez.entity.Restaurant;
import com.naivez.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReviewRepository extends JpaRepository<Review, Long>, QuerydslPredicateExecutor<Review>, FilterReviewRepository {
}
