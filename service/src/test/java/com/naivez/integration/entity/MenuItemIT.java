package com.naivez.integration.entity;

import com.naivez.entity.MenuItem;
import com.naivez.entity.Restaurant;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MenuItemIT extends IntegrationTestBase {

    @Test
    void addMenuItem() {
        Session session = getSession();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);
        session.flush();

        var menuItem = MenuItem.builder()
                .name("Some Pizza")
                .price(new BigDecimal("10.00"))
                .description("Some description")
                .restaurant(restaurant)
                .build();
        session.persist(menuItem);
        session.clear();

        var actualMenuItem = session.get(MenuItem.class, menuItem.getId());

        assertThat(actualMenuItem).isNotNull();
        assertThat(actualMenuItem.getId()).isNotNull();
        assertEquals("Some Pizza", actualMenuItem.getName());
        assertEquals(new BigDecimal("10.00"), actualMenuItem.getPrice());
    }

    @Test
    void getMenuItem() {
        Session session = getSession();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);
        session.flush();

        var menuItem = MenuItem.builder()
                .name("Some Pizza")
                .price(new BigDecimal("10.00"))
                .description("Some description")
                .restaurant(restaurant)
                .build();
        session.persist(menuItem);
        session.clear();

        var actualMenuItem = session.getReference(MenuItem.class, menuItem.getId());
        assertEquals(menuItem, actualMenuItem);
    }

    @Test
    void updateMenuItem() {
        Session session = getSession();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);
        session.flush();

        var menuItem = MenuItem.builder()
                .name("Some Pizza")
                .price(new BigDecimal("10.00"))
                .description("Some description")
                .restaurant(restaurant)
                .build();
        session.persist(menuItem);
        session.clear();

        menuItem.setPrice(new BigDecimal("11.00"));
        menuItem.setDescription("Updated description");
        session.merge(menuItem);
        session.flush();
        session.clear();

        var updatedMenuItem = session.get(MenuItem.class, menuItem.getId());

        assertEquals(new BigDecimal("11.00"), updatedMenuItem.getPrice());
        assertEquals("Updated description", updatedMenuItem.getDescription());
    }

    @Test
    void deleteMenuItem() {
        Session session = getSession();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
        session.persist(restaurant);
        session.flush();

        var menuItem = MenuItem.builder()
                .name("Some Pizza")
                .price(new BigDecimal("10.00"))
                .description("Some description")
                .restaurant(restaurant)
                .build();
        session.persist(menuItem);
        session.flush();
        session.clear();

        MenuItem managedMenuItem = session.get(MenuItem.class, menuItem.getId());
        assertThat(managedMenuItem).isNotNull();

        session.remove(managedMenuItem);
        session.flush();
        session.clear();

        assertNull(session.get(MenuItem.class, menuItem.getId()));
    }
}
