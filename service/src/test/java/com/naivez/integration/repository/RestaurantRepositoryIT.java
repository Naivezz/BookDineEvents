package com.naivez.integration.repository;

import com.naivez.annotation.IT;
import com.naivez.entity.Restaurant;
import com.naivez.repository.RestaurantRepository;
import com.naivez.util.DataBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class RestaurantRepositoryIT {

    private final RestaurantRepository restaurantRepository;

    @Test
    void saveRestaurant() {
        var restaurant = DataBuilder.createRestaurant();

        restaurantRepository.save(restaurant);

        assertThat(restaurantRepository.findById(restaurant.getId())).isPresent();
    }

    @Test
    void deleteRestaurant() {
        var restaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(restaurant);

        restaurantRepository.delete(restaurant);

        assertThat(restaurantRepository.findById(restaurant.getId())).isEmpty();
    }

    @Test
    void updateRestaurant() {
        var restaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(restaurant);

        restaurant.setName("Updated Restaurant");
        restaurantRepository.update(restaurant);

        assertThat(restaurantRepository.findById(restaurant.getId()))
                .map(Restaurant::getName)
                .contains("Updated Restaurant");
    }

    @Test
    void findByIdRestaurant() {
        var restaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(restaurant);

        var expectedRestaurant = restaurantRepository.findById(restaurant.getId());

        assertThat(expectedRestaurant).isPresent();
        assertThat(expectedRestaurant.get().getId()).isEqualTo(restaurant.getId());
    }

    @Test
    void findAllRestaurants() {
        var restaurant1 = DataBuilder.createRestaurant();
        var restaurant2 = DataBuilder.createRestaurant();
        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);

        var restaurants = restaurantRepository.findAll();

        assertThat(restaurants).hasSize(2);
    }
}
