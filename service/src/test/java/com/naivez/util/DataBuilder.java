package com.naivez.util;

import com.naivez.entity.BlackList;
import com.naivez.entity.Event;
import com.naivez.entity.MenuItem;
import com.naivez.entity.Order;
import com.naivez.entity.OrderMenuItem;
import com.naivez.entity.Payment;
import com.naivez.entity.Reservation;
import com.naivez.entity.Restaurant;
import com.naivez.entity.Review;
import com.naivez.entity.Spot;
import com.naivez.entity.User;
import com.naivez.entity.enums.OrderStatus;
import com.naivez.entity.enums.PaymentStatus;
import com.naivez.entity.enums.ReservationStatus;
import com.naivez.entity.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DataBuilder {

    public static User createUser(){
        return User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();
    }

    public static Restaurant createRestaurant(){
        return Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();
    }

    public static Spot createSpot(){
        return Spot.builder()
                .tableNumber(10)
                .seats(4)
                .restaurant(createRestaurant())
                .build();
    }

    public static Reservation createReservation(){
        return Reservation.builder()
                .guests(5)
                .status(ReservationStatus.CONFIRMED)
                .build();
    }

    public static Payment createPayment(){
        return Payment.builder()
                .amount(new BigDecimal("150.00"))
                .status(PaymentStatus.PENDING)
                .reservation(createReservation())
                .build();
    }

    public static MenuItem createMenuItem(){
        return MenuItem.builder()
                .name("Some Pizza")
                .price(new BigDecimal("10.00"))
                .description("Some description")
                .restaurant(createRestaurant())
                .build();
    }

    public static Order createOrder(){
        return Order.builder()
                .time(LocalDateTime.now().plusHours(1))
                .status(OrderStatus.PENDING)
                .reservation(createReservation())
                .user(createUser())
                .restaurant(createRestaurant())
                .build();
    }

    public static OrderMenuItem createOrderMenuItem(){
        return OrderMenuItem.builder()
                .quantity(2)
                .order(createOrder())
                .menuItem(createMenuItem())
                .build();
    }

    public static Review createReview(){
        return Review.builder()
                .rating(4)
                .description("Great")
                .user(createUser())
                .restaurant(createRestaurant())
                .build();
    }

    public static Event createEvent(){
        return Event.builder()
                .name("Some event")
                .description("Some description")
                .restaurant(createRestaurant())
                .build();
    }

    public static BlackList createBlackList(){
        return BlackList.builder()
                .reason("Some reason")
                .user(createUser())
                .restaurant(createRestaurant())
                .build();
    }

}
