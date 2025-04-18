package com.naivez.integration.repository;

import com.naivez.entity.Review;
import com.naivez.repository.ReviewRepository;
import com.naivez.repository.RestaurantRepository;
import com.naivez.repository.UserRepository;
import com.naivez.util.DataBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ReviewRepositoryIT extends IntegrationTestBase {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Test
    void saveReview() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        var review = DataBuilder.createReview();
        restaurantRepository.save(restaurant);
        userRepository.save(user);
        review.setRestaurant(restaurant);
        review.setUser(user);

        reviewRepository.save(review);

        assertThat(reviewRepository.findById(review.getId())).isPresent();
    }

    @Test
    void deleteReview() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        var review = DataBuilder.createReview();
        restaurantRepository.save(restaurant);
        userRepository.save(user);
        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);

        reviewRepository.delete(review);

        assertThat(reviewRepository.findById(review.getId())).isEmpty();
    }

    @Test
    void updateReview() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        var review = DataBuilder.createReview();
        restaurantRepository.save(restaurant);
        userRepository.save(user);
        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);


        review.setRating(4);
        review.setDescription("Updated description");
        reviewRepository.save(review);

        assertThat(reviewRepository.findById(review.getId()))
                .map(Review::getRating)
                .contains(4);
        assertThat(reviewRepository.findById(review.getId()))
                .map(Review::getDescription)
                .contains("Updated description");
    }

    @Test
    void findByIdReview() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        var review = DataBuilder.createReview();
        restaurantRepository.save(restaurant);
        userRepository.save(user);
        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);

        var expectedReview = reviewRepository.findById(review.getId());

        assertThat(expectedReview).isPresent();
        assertThat(expectedReview.get().getId()).isEqualTo(review.getId());
    }

    @Test
    void findAllReviews() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        var review1 = DataBuilder.createReview();
        var review2 = DataBuilder.createReview();
        restaurantRepository.save(restaurant);
        userRepository.save(user);
        review1.setRestaurant(restaurant);
        review1.setUser(user);
        review2.setRestaurant(restaurant);
        review2.setUser(user);
        reviewRepository.save(review1);
        reviewRepository.save(review2);

        var reviews = reviewRepository.findAll();

        assertThat(reviews).hasSize(2);
    }

}
