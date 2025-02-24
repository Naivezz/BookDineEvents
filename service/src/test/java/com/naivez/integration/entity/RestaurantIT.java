package com.naivez.integration.entity;

import com.naivez.entity.Restaurant;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RestaurantIT extends IntegrationTestBase {

    @Test
    void addRestaurant() {
        Session session = getSession();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);
        session.clear();

        var actualRestaurant = session.get(Restaurant.class, restaurant.getId());

        assertThat(actualRestaurant).isNotNull();
        assertThat(actualRestaurant.getId()).isNotNull();
        assertEquals("Test name", actualRestaurant.getName());
    }

    @Test
    void getRestaurant() {
        Session session = getSession();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);
        session.clear();

        var actualRestaurant = session.getReference(Restaurant.class, restaurant.getId());
        assertEquals(restaurant, actualRestaurant);
    }

    @Test
    void updateRestaurant() {
        Session session = getSession();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);
        session.clear();

        restaurant.setName("Update Name");
        restaurant.setPhoneNumber("+422352532");
        session.merge(restaurant);
        session.flush();
        session.clear();

        var updatedRestaurant = session.get(Restaurant.class, restaurant.getId());

        assertEquals("Update Name", updatedRestaurant.getName());
        assertEquals("+422352532", updatedRestaurant.getPhoneNumber());
    }

    @Test
    void deleteRestaurant() {
        Session session = getSession();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);
        session.flush();
        session.clear();

        Restaurant managedRestaurant = session.get(Restaurant.class, restaurant.getId());
        assertThat(managedRestaurant).isNotNull();

        session.remove(managedRestaurant);
        session.flush();
        session.clear();

        assertNull(session.get(Restaurant.class, restaurant.getId()));
    }
}
