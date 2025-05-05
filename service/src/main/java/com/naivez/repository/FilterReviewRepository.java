package com.naivez.repository;

import com.naivez.dto.review.ReviewFilter;
import com.naivez.entity.Review;

import java.util.List;

public interface FilterReviewRepository {

    List<Review> findAllByFilter(ReviewFilter filter);

}
