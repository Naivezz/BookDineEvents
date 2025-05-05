package com.naivez.repository;

import com.naivez.dto.review.ReviewFilter;
import com.naivez.entity.Review;
import com.naivez.repository.predicate.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.naivez.entity.QReview.review;

@RequiredArgsConstructor
public class FilterReviewRepositoryImpl implements FilterReviewRepository {

    private final EntityManager entityManager;

    @Override
    public List<Review> findAllByFilter(ReviewFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getRating(), review.rating::eq)
                .build();

        return new JPAQuery<Review>(entityManager)
                .select(review)
                .from(review)
                .where(predicate)
                .fetch();
    }
}
