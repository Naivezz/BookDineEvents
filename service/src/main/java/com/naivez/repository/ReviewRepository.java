package com.naivez.repository;

import com.naivez.entity.Review;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository extends RepositoryBase<Long, Review>{
    public ReviewRepository(EntityManager entityManager) {
        super(Review.class, entityManager);
    }
}
