package com.naivez.integration.dao;

import com.naivez.dao.OrderMenuItemRepository;
import com.naivez.entity.OrderMenuItem;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMenuItemRepositoryIT extends IntegrationTestBase {

    private OrderMenuItemRepository orderMenuItemRepository;

    @BeforeEach
    void setUp() {
        orderMenuItemRepository = new OrderMenuItemRepository(OrderMenuItem.class, session);
    }

    @Test
    void createOrderMenuItem() {
        var user = DataBuilder.createUser();
        session.persist(user);
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var order = DataBuilder.createOrder();
        order.setUser(user);
        order.setRestaurant(restaurant);
        session.persist(order);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        session.persist(menuItem);
        var testOrderMenuItem = DataBuilder.createOrderMenuItem();
        testOrderMenuItem.setOrder(order);
        testOrderMenuItem.setMenuItem(menuItem);

        orderMenuItemRepository.save(testOrderMenuItem);
        session.clear();

        assertThat(session.get(OrderMenuItem.class, testOrderMenuItem.getId())).isNotNull();
    }

    @Test
    void updateOrderMenuItem() {
        var user = DataBuilder.createUser();
        session.persist(user);
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var order = DataBuilder.createOrder();
        order.setUser(user);
        order.setRestaurant(restaurant);
        session.persist(order);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        session.persist(menuItem);
        var testOrderMenuItem = DataBuilder.createOrderMenuItem();
        testOrderMenuItem.setOrder(order);
        testOrderMenuItem.setMenuItem(menuItem);
        orderMenuItemRepository.save(testOrderMenuItem);
        session.clear();

        testOrderMenuItem.setQuantity(2);
        orderMenuItemRepository.update(testOrderMenuItem);
        session.flush();
        session.clear();
        OrderMenuItem updatedOrderMenuItem = session.get(OrderMenuItem.class, testOrderMenuItem.getId());

        assertThat(updatedOrderMenuItem).isEqualTo(testOrderMenuItem);
    }

    @Test
    void findOrderMenuItemById() {
        var user = DataBuilder.createUser();
        session.persist(user);
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var order = DataBuilder.createOrder();
        order.setUser(user);
        order.setRestaurant(restaurant);
        session.persist(order);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        session.persist(menuItem);
        var testOrderMenuItem = DataBuilder.createOrderMenuItem();
        testOrderMenuItem.setOrder(order);
        testOrderMenuItem.setMenuItem(menuItem);
        orderMenuItemRepository.save(testOrderMenuItem);
        session.clear();

        var expectedOrderMenuItem = orderMenuItemRepository.findById(testOrderMenuItem.getId());

        assertThat(expectedOrderMenuItem).isEqualTo(Optional.of(testOrderMenuItem));
    }


    @Test
    void deleteOrderMenuItem() {
        var user = DataBuilder.createUser();
        session.persist(user);
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var order = DataBuilder.createOrder();
        order.setUser(user);
        order.setRestaurant(restaurant);
        session.persist(order);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        session.persist(menuItem);
        var testOrderMenuItem = DataBuilder.createOrderMenuItem();
        testOrderMenuItem.setOrder(order);
        testOrderMenuItem.setMenuItem(menuItem);
        orderMenuItemRepository.save(testOrderMenuItem);
        session.clear();

        orderMenuItemRepository.delete(testOrderMenuItem.getId());

        assertThat(session.get(OrderMenuItem.class, testOrderMenuItem.getId())).isNull();
    }
}
