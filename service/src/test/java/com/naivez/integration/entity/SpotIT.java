package com.naivez.integration.entity;

import com.naivez.entity.Restaurant;
import com.naivez.entity.Spot;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SpotIT extends IntegrationTestBase {

    @Test
    void addSpot() {
        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);

        var spot = Spot.builder()
                .tableNumber(10)
                .seats(4)
                .restaurant(restaurant)
                .build();
        session.persist(spot);
        session.flush();
        session.clear();

        var actualSpot = session.get(Spot.class, spot.getId());
        assertThat(actualSpot).isNotNull();
        assertThat(actualSpot.getId()).isNotNull();
        assertEquals(10, actualSpot.getTableNumber());
    }

    @Test
    void getSpot() {
        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);

        var spot = Spot.builder()
                .tableNumber(10)
                .seats(4)
                .restaurant(restaurant)
                .build();
        session.persist(spot);
        session.flush();
        session.clear();

        var actualSpot = session.get(Spot.class, spot.getId());
        assertEquals(spot, actualSpot);
    }

    @Test
    void updateSpot() {
        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);

        var spot = Spot.builder()
                .tableNumber(10)
                .seats(4)
                .restaurant(restaurant)
                .build();
        session.persist(spot);
        session.flush();
        session.clear();

        spot.setSeats(8);
        session.merge(spot);
        session.flush();
        session.clear();

        var updatedSpot = session.get(Spot.class, spot.getId());
        assertEquals(8, updatedSpot.getSeats());
    }

    @Test
    void deleteSpot() {
        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);

        var spot = Spot.builder()
                .tableNumber(10)
                .seats(4)
                .restaurant(restaurant)
                .build();
        session.persist(spot);
        session.flush();
        session.clear();

        Spot managedSpot = session.get(Spot.class, spot.getId());
        assertThat(managedSpot).isNotNull();

        session.remove(managedSpot);
        session.flush();
        session.clear();

        assertNull(session.get(Spot.class, spot.getId()));
    }
}
