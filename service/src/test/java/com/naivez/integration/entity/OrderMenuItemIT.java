package com.naivez.integration.entity;

import com.naivez.entity.MenuItem;
import com.naivez.entity.Order;
import com.naivez.entity.OrderMenuItem;
import com.naivez.entity.Restaurant;
import com.naivez.entity.User;
import com.naivez.entity.enums.OrderStatus;
import com.naivez.entity.enums.Role;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderMenuItemIT extends IntegrationTestBase {

    @Test
    void addOrderMenuItem() {
        Session session = getSession();

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

        var menuItem = MenuItem.builder()
                .name("Some Pizza")
                .price(new BigDecimal("10.00"))
                .description("Some description")
                .restaurant(restaurant)
                .build();

        var order = Order.builder()
                .time(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.persist(menuItem);
        session.persist(order);
        session.flush();

        var orderMenuItem = OrderMenuItem.builder()
                .quantity(2)
                .order(order)
                .menuItem(menuItem)
                .build();

        session.persist(orderMenuItem);
        session.flush();
        session.clear();

        var savedOrderMenuItem = session.get(OrderMenuItem.class, orderMenuItem.getId());

        assertThat(savedOrderMenuItem).isNotNull();
        assertThat(savedOrderMenuItem.getId()).isNotNull();
        assertEquals(2, savedOrderMenuItem.getQuantity());
        assertEquals(order, savedOrderMenuItem.getOrder());
        assertEquals(menuItem, savedOrderMenuItem.getMenuItem());
    }

    @Test
    void getOrderMenuItem() {
        Session session = getSession();

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

        var menuItem = MenuItem.builder()
                .name("Some Pizza")
                .price(new BigDecimal("10.00"))
                .description("Some description")
                .restaurant(restaurant)
                .build();

        var order = Order.builder()
                .time(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.persist(menuItem);
        session.persist(order);
        session.flush();

        var orderMenuItem = OrderMenuItem.builder()
                .quantity(1)
                .order(order)
                .menuItem(menuItem)
                .build();

        session.persist(orderMenuItem);
        session.flush();
        session.clear();

        var retrievedOrderMenuItem = session.getReference(OrderMenuItem.class, orderMenuItem.getId());

        assertEquals(orderMenuItem, retrievedOrderMenuItem);
    }

    @Test
    void updateOrderMenuItem() {
        Session session = getSession();

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

        var menuItem = MenuItem.builder()
                .name("Some Pizza")
                .price(new BigDecimal("10.00"))
                .description("Some description")
                .restaurant(restaurant)
                .build();

        var order = Order.builder()
                .time(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.persist(menuItem);
        session.persist(order);
        session.flush();

        var orderMenuItem = OrderMenuItem.builder()
                .quantity(3)
                .order(order)
                .menuItem(menuItem)
                .build();

        session.persist(orderMenuItem);
        session.clear();

        orderMenuItem.setQuantity(4);
        session.merge(orderMenuItem);
        session.flush();
        session.clear();

        var updatedOrderMenuItem = session.get(OrderMenuItem.class, orderMenuItem.getId());

        assertEquals(4, updatedOrderMenuItem.getQuantity());
    }

    @Test
    void deleteOrderMenuItem() {
        Session session = getSession();

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

        var menuItem = MenuItem.builder()
                .name("Some Pizza")
                .price(new BigDecimal("10.00"))
                .description("Some description")
                .restaurant(restaurant)
                .build();

        var order = Order.builder()
                .time(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.persist(menuItem);
        session.persist(order);
        session.flush();

        var orderMenuItem = OrderMenuItem.builder()
                .quantity(5)
                .order(order)
                .menuItem(menuItem)
                .build();

        session.persist(orderMenuItem);
        session.flush();
        session.clear();

        var managedOrderMenuItem = session.get(OrderMenuItem.class, orderMenuItem.getId());
        assertThat(managedOrderMenuItem).isNotNull();

        session.remove(managedOrderMenuItem);
        session.flush();
        session.clear();

        assertNull(session.get(OrderMenuItem.class, orderMenuItem.getId()));
    }
}
