package com.naivez.integration.dao;

import com.naivez.dao.RestaurantRepository;
import com.naivez.entity.Restaurant;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantRepositoryIT extends IntegrationTestBase {

    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        restaurantRepository = new RestaurantRepository(Restaurant.class, session);
    }

    @Test
    void createRestaurant() {
        var testRestaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(testRestaurant);
        session.clear();

        assertThat(session.get(Restaurant.class, testRestaurant.getId())).isNotNull();
    }

    @Test
    void updateRestaurant() {
        var testRestaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(testRestaurant);
        session.clear();
        testRestaurant.setName("Updated Restaurant Name");

        restaurantRepository.update(testRestaurant);
        session.flush();
        session.clear();
        Restaurant updatedRestaurant = session.get(Restaurant.class, testRestaurant.getId());

        assertThat(updatedRestaurant).isEqualTo(testRestaurant);
    }

    @Test
    void findRestaurantById() {
        var testRestaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(testRestaurant);
        session.clear();

        var expectedRestaurant = restaurantRepository.findById(testRestaurant.getId());

        assertThat(expectedRestaurant).isEqualTo(Optional.of(testRestaurant));
    }

    @Test
    void findAllRestaurants() {
        var testRestaurant1 = DataBuilder.createRestaurant();
        restaurantRepository.save(testRestaurant1);
        session.flush();
        var testRestaurant2 = DataBuilder.createRestaurant();
        restaurantRepository.save(testRestaurant2);
        session.flush();
        session.clear();

        List<Restaurant> restaurants = restaurantRepository.findAll();

        assertThat(restaurants)
                .isNotEmpty()
                .containsExactlyInAnyOrder(testRestaurant1, testRestaurant2);
    }

    @Test
    void deleteRestaurant() {
        var testRestaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(testRestaurant);

        restaurantRepository.delete(testRestaurant.getId());

        assertThat(session.get(Restaurant.class, testRestaurant.getId())).isNull();
    }
}
