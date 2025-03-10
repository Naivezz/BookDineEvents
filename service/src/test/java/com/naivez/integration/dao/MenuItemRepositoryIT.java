package com.naivez.integration.dao;

import com.naivez.dao.MenuItemRepository;
import com.naivez.entity.MenuItem;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MenuItemRepositoryIT extends IntegrationTestBase {

    private MenuItemRepository menuItemRepository;

    @BeforeEach
    void setUp() {
        menuItemRepository = new MenuItemRepository(MenuItem.class, session);
    }

    @Test
    void createMenuItem() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);

        menuItemRepository.save(menuItem);
        session.clear();

        assertThat(session.get(MenuItem.class, menuItem.getId())).isNotNull();
    }

    @Test
    void updateMenuItem() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
        session.clear();

        menuItem.setName("Updated name");
        menuItemRepository.update(menuItem);
        session.flush();
        session.clear();
        MenuItem updatedMenuItem = session.get(MenuItem.class, menuItem.getId());

        assertThat(updatedMenuItem).isEqualTo(menuItem);
    }

    @Test
    void findMenuItemById() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
        session.clear();

        var expectedMenuItem = menuItemRepository.findById(menuItem.getId());

        assertThat(expectedMenuItem).isEqualTo(Optional.of(menuItem));
    }

    @Test
    void findAllMenuItems() {
        var restaurant1 = DataBuilder.createRestaurant();
        session.persist(restaurant1);
        session.flush();
        var restaurant2 = DataBuilder.createRestaurant();
        session.persist(restaurant2);
        session.flush();
        var menuItem1 = DataBuilder.createMenuItem();
        menuItem1.setRestaurant(restaurant1);
        menuItemRepository.save(menuItem1);
        session.flush();
        var menuItem2 = DataBuilder.createMenuItem();
        menuItem2.setRestaurant(restaurant2);
        menuItemRepository.save(menuItem2);
        session.flush();
        session.clear();

        List<MenuItem> menuItems = menuItemRepository.findAll();

        assertThat(menuItems)
                .isNotEmpty()
                .containsExactlyInAnyOrder(menuItem1, menuItem2);
    }

    @Test
    void deleteMenuItem() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
        session.clear();

        menuItemRepository.delete(menuItem.getId());

        assertThat(session.get(MenuItem.class, menuItem.getId())).isNull();
    }
}
