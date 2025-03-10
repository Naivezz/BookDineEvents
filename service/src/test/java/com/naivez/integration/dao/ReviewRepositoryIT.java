package com.naivez.integration.dao;

import com.naivez.dao.ReviewRepository;
import com.naivez.entity.Review;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewRepositoryIT extends IntegrationTestBase {

    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository = new ReviewRepository(Review.class, session);
    }

    @Test
    void createReview() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        session.persist(restaurant);
        session.persist(user);
        session.flush();
        var review = DataBuilder.createReview();
        review.setRestaurant(restaurant);
        review.setUser(user);

        reviewRepository.save(review);
        session.clear();

        assertThat(session.get(Review.class, review.getId())).isNotNull();
    }

    @Test
    void updateReview() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        session.persist(restaurant);
        session.persist(user);
        session.flush();
        var review = DataBuilder.createReview();
        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);
        session.clear();

        review.setRating(4);
        reviewRepository.update(review);
        session.flush();
        session.clear();
        Review updatedReview = session.get(Review.class, review.getId());

        assertThat(updatedReview).isEqualTo(review);
    }

    @Test
    void findReviewById() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        session.persist(restaurant);
        session.persist(user);
        session.flush();
        var review = DataBuilder.createReview();
        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);
        session.clear();

        var expectedReview = reviewRepository.findById(review.getId());

        assertThat(expectedReview).isEqualTo(Optional.of(review));
    }

    @Test
    void findAllReviews() {
        var restaurant1 = DataBuilder.createRestaurant();
        var user1 = DataBuilder.createUser();
        session.persist(restaurant1);
        session.persist(user1);
        session.flush();
        var restaurant2 = DataBuilder.createRestaurant();
        var user2 = DataBuilder.createUser();
        session.persist(restaurant2);
        session.persist(user2);
        session.flush();
        var review1 = DataBuilder.createReview();
        review1.setRestaurant(restaurant1);
        review1.setUser(user1);
        reviewRepository.save(review1);
        session.flush();
        var review2 = DataBuilder.createReview();
        review2.setRestaurant(restaurant2);
        review2.setUser(user2);
        reviewRepository.save(review2);
        session.flush();
        session.clear();

        List<Review> reviews = reviewRepository.findAll();

        assertThat(reviews)
                .isNotEmpty()
                .containsExactlyInAnyOrder(review1, review2);
    }

    @Test
    void deleteReview() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        session.persist(restaurant);
        session.persist(user);
        session.flush();
        var review = DataBuilder.createReview();
        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);
        session.clear();

        reviewRepository.delete(review.getId());

        assertThat(session.get(Review.class, review.getId())).isNull();
    }
}
