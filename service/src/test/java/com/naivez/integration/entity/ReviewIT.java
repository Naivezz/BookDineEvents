package com.naivez.integration.entity;

import com.naivez.entity.Review;
import com.naivez.entity.Restaurant;
import com.naivez.entity.User;
import com.naivez.entity.enums.Role;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReviewIT extends IntegrationTestBase {

    @Test
    void addReview() {
        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.flush();

        var review = Review.builder()
                .rating(4)
                .description("Great")
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(review);
        session.flush();
        session.clear();

        var savedReview = session.get(Review.class, review.getId());

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getId()).isNotNull();
        assertEquals(4, savedReview.getRating());
        assertEquals("Great", savedReview.getDescription());
        assertEquals(user, savedReview.getUser());
        assertEquals(restaurant, savedReview.getRestaurant());
    }

    @Test
    void getReview() {
        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.flush();

        var review = Review.builder()
                .rating(5)
                .description("Great")
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(review);
        session.flush();
        session.clear();

        var retrievedReview = session.getReference(Review.class, review.getId());

        assertEquals(review, retrievedReview);
    }

    @Test
    void updateReview() {
        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.flush();

        var review = Review.builder()
                .rating(3)
                .description("Great")
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(review);
        session.clear();

        review.setRating(4);
        review.setDescription("Good");
        session.merge(review);
        session.flush();
        session.clear();

        var updatedReview = session.get(Review.class, review.getId());

        assertEquals(4, updatedReview.getRating());
        assertEquals("Good", updatedReview.getDescription());
    }

    @Test
    void deleteReview() {
        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.flush();

        var review = Review.builder()
                .rating(5)
                .description("Great")
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(review);
        session.flush();
        session.clear();

        var managedReview = session.get(Review.class, review.getId());
        assertThat(managedReview).isNotNull();

        session.remove(managedReview);
        session.flush();
        session.clear();

        assertNull(session.get(Review.class, review.getId()));
    }
}
