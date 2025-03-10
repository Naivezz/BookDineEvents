package com.naivez.integration.dao;

import com.naivez.dao.OrderRepository;
import com.naivez.entity.Order;
import com.naivez.entity.enums.OrderStatus;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRepositoryIT extends IntegrationTestBase {

    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository(Order.class, session);
    }

    @Test
    void createOrder() {
        var reservation = DataBuilder.createReservation();
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        session.persist(reservation);
        session.persist(restaurant);
        session.persist(user);
        session.flush();
        var order = DataBuilder.createOrder();
        order.setReservation(reservation);
        order.setRestaurant(restaurant);
        order.setUser(user);

        orderRepository.save(order);
        session.clear();

        assertThat(session.get(Order.class, order.getId())).isNotNull();
    }

    @Test
    void updateOrder() {
        var reservation = DataBuilder.createReservation();
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        session.persist(reservation);
        session.persist(restaurant);
        session.persist(user);
        session.flush();
        var order = DataBuilder.createOrder();
        order.setReservation(reservation);
        order.setRestaurant(restaurant);
        order.setUser(user);
        orderRepository.save(order);
        session.clear();

        order.setStatus(OrderStatus.PREPARING);
        orderRepository.update(order);
        session.flush();
        session.clear();
        Order updatedOrder = session.get(Order.class, order.getId());

        assertThat(updatedOrder).isEqualTo(order);
    }

    @Test
    void findOrderById() {
        var reservation = DataBuilder.createReservation();
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        session.persist(reservation);
        session.persist(restaurant);
        session.persist(user);
        session.flush();
        var order = DataBuilder.createOrder();
        order.setReservation(reservation);
        order.setRestaurant(restaurant);
        order.setUser(user);
        orderRepository.save(order);
        session.clear();

        var expectedOrder = orderRepository.findById(order.getId());

        assertThat(expectedOrder).isEqualTo(Optional.of(order));
    }

    @Test
    void findAllOrders() {
        var reservation1 = DataBuilder.createReservation();
        var restaurant1 = DataBuilder.createRestaurant();
        var user1 = DataBuilder.createUser();
        session.persist(reservation1);
        session.persist(restaurant1);
        session.persist(user1);
        session.flush();
        var reservation2 = DataBuilder.createReservation();
        var restaurant2 = DataBuilder.createRestaurant();
        var user2 = DataBuilder.createUser();
        session.persist(reservation2);
        session.persist(restaurant2);
        session.persist(user2);
        session.flush();
        var order1 = DataBuilder.createOrder();
        order1.setReservation(reservation1);
        order1.setRestaurant(restaurant1);
        order1.setUser(user1);
        orderRepository.save(order1);
        session.flush();
        var order2 = DataBuilder.createOrder();
        order2.setReservation(reservation2);
        order2.setRestaurant(restaurant2);
        order2.setUser(user2);
        orderRepository.save(order2);
        session.flush();
        session.clear();

        List<Order> orders = orderRepository.findAll();

        assertThat(orders)
                .isNotEmpty()
                .containsExactlyInAnyOrder(order1, order2);
    }

    @Test
    void deleteOrder() {
        var reservation = DataBuilder.createReservation();
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        session.persist(reservation);
        session.persist(restaurant);
        session.persist(user);
        session.flush();
        var order = DataBuilder.createOrder();
        order.setReservation(reservation);
        order.setRestaurant(restaurant);
        order.setUser(user);
        orderRepository.save(order);
        session.clear();

        orderRepository.delete(order.getId());

        assertThat(session.get(Order.class, order.getId())).isNull();
    }
}
