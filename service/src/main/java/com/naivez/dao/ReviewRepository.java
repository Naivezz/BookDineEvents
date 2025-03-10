package com.naivez.dao;

import com.naivez.entity.Review;
import jakarta.persistence.EntityManager;

public class ReviewRepository extends RepositoryBase<Long, Review>{

    public ReviewRepository(Class<Review> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
