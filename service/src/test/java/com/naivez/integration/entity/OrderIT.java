package com.naivez.integration.entity;

import com.naivez.entity.Order;
import com.naivez.entity.Reservation;
import com.naivez.entity.Restaurant;
import com.naivez.entity.User;
import com.naivez.entity.enums.OrderStatus;
import com.naivez.entity.enums.ReservationStatus;
import com.naivez.entity.enums.Role;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderIT extends IntegrationTestBase {

    @Test
    void addOrder() {
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

        var reservation = Reservation.builder()
                .time(LocalDateTime.now().plusDays(5))
                .guests(3)
                .status(ReservationStatus.PENDING)
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.persist(reservation);
        session.flush();

        var expectedOrder = Order.builder()
                .time(LocalDateTime.now().plusHours(1))
                .status(OrderStatus.PENDING)
                .reservation(reservation)
                .user(user)
                .restaurant(restaurant)
                .build();
        session.persist(expectedOrder);
        session.clear();

        var actualOrder = session.get(Order.class, expectedOrder.getId());

        assertThat(actualOrder).isNotNull();
        assertThat(actualOrder.getId()).isNotNull();
        assertEquals(OrderStatus.PENDING, actualOrder.getStatus());
        assertEquals(user, actualOrder.getUser());
        assertEquals(restaurant, actualOrder.getRestaurant());
    }

    @Test
    void getOrder() {
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

        var reservation = Reservation.builder()
                .time(LocalDateTime.now().plusDays(5))
                .guests(3)
                .status(ReservationStatus.PENDING)
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.persist(reservation);
        session.flush();

        var order = Order.builder()
                .time(LocalDateTime.now().plusMinutes(30))
                .status(OrderStatus.PENDING)
                .reservation(reservation)
                .user(user)
                .restaurant(restaurant)
                .build();
        session.persist(order);
        session.clear();

        var actualOrder = session.get(Order.class, order.getId());
        assertEquals(order, actualOrder);
    }

    @Test
    void updateOrder() {
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

        var reservation = Reservation.builder()
                .time(LocalDateTime.now().plusDays(5))
                .guests(3)
                .status(ReservationStatus.PENDING)
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.persist(reservation);
        session.flush();

        var order = Order.builder()
                .time(LocalDateTime.now().plusMinutes(20))
                .status(OrderStatus.PENDING)
                .reservation(reservation)
                .user(user)
                .restaurant(restaurant)
                .build();
        session.persist(order);
        session.clear();

        order.setStatus(OrderStatus.READY_FOR_PICKUP);
        session.merge(order);
        session.flush();
        session.clear();

        var updatedOrder = session.get(Order.class, order.getId());

        assertEquals(OrderStatus.READY_FOR_PICKUP, updatedOrder.getStatus());
    }

    @Test
    void deleteOrder() {
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

        var reservation = Reservation.builder()
                .time(LocalDateTime.now().plusDays(5))
                .guests(3)
                .status(ReservationStatus.PENDING)
                .build();

        session.persist(user);
        session.persist(restaurant);
        session.persist(reservation);
        session.flush();

        var order = Order.builder()
                .time(LocalDateTime.now().plusMinutes(45))
                .status(OrderStatus.PENDING)
                .reservation(reservation)
                .user(user)
                .restaurant(restaurant)
                .build();
        session.persist(order);
        session.flush();
        session.clear();

        Order managedOrder = session.get(Order.class, order.getId());
        assertThat(managedOrder).isNotNull();

        session.remove(managedOrder);
        session.flush();
        session.clear();

        assertNull(session.get(Order.class, order.getId()));
    }
}
