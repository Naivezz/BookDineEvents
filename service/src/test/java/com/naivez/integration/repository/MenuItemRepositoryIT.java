package com.naivez.integration.repository;

import com.naivez.entity.MenuItem;
import com.naivez.repository.MenuItemRepository;
import com.naivez.repository.RestaurantRepository;
import com.naivez.util.DataBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class MenuItemRepositoryIT extends IntegrationTestBase {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Test
    void saveMenuItem() {
        var restaurant = DataBuilder.createRestaurant();
        var menuItem = DataBuilder.createMenuItem();
        restaurantRepository.save(restaurant);
        menuItem.setRestaurant(restaurant);

        menuItemRepository.save(menuItem);

        assertThat(menuItemRepository.findById(menuItem.getId())).isPresent();
    }

    @Test
    void deleteMenuItem() {
        var restaurant = DataBuilder.createRestaurant();
        var menuItem = DataBuilder.createMenuItem();
        restaurantRepository.save(restaurant);
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);

        menuItemRepository.delete(menuItem);

        assertThat(menuItemRepository.findById(menuItem.getId())).isEmpty();
    }

    @Test
    void updateMenuItem() {
        var restaurant = DataBuilder.createRestaurant();
        var menuItem = DataBuilder.createMenuItem();
        restaurantRepository.save(restaurant);
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);

        menuItem.setDescription("Updated description");
        menuItemRepository.save(menuItem);

        assertThat(menuItemRepository.findById(menuItem.getId()))
                .map(MenuItem::getDescription)
                .contains("Updated description");
    }

    @Test
    void findByIdMenuItem() {
        var restaurant = DataBuilder.createRestaurant();
        var menuItem = DataBuilder.createMenuItem();
        restaurantRepository.save(restaurant);
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);

        var expectedMenuItem = menuItemRepository.findById(menuItem.getId());

        assertThat(expectedMenuItem).isPresent();
        assertThat(expectedMenuItem.get().getId()).isEqualTo(menuItem.getId());
    }

    @Test
    void findAllMenuItems() {
        var restaurant = DataBuilder.createRestaurant();
        var menuItem1 = DataBuilder.createMenuItem();
        var menuItem2 = DataBuilder.createMenuItem();
        restaurantRepository.save(restaurant);
        menuItem1.setRestaurant(restaurant);
        menuItem2.setRestaurant(restaurant);
        menuItemRepository.save(menuItem1);
        menuItemRepository.save(menuItem2);

        var menuItems = menuItemRepository.findAll();

        assertThat(menuItems).hasSize(2);
    }
}
