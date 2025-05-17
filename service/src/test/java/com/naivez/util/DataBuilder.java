package com.naivez.util;

import com.naivez.entity.Event;
import com.naivez.entity.MenuItem;
import com.naivez.entity.Reservation;
import com.naivez.entity.ReservationMenuItem;
import com.naivez.entity.Restaurant;
import com.naivez.entity.Review;
import com.naivez.entity.User;
import com.naivez.entity.enums.PaymentStatus;
import com.naivez.entity.enums.ReservationStatus;
import com.naivez.entity.enums.Role;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DataBuilder {

    public static User createUser(){
        String uniqueEmail = "test" + System.currentTimeMillis() + (int)(Math.random() * 1000) + "@gmail.com";
        return User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email(uniqueEmail)
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

    public static Reservation createReservation() {
        return Reservation.builder()
                .time(Instant.now())
                .guests(5)
                .status(ReservationStatus.CONFIRMED)
                .amount(BigDecimal.valueOf(100.00))
                .paymentTime(null)
                .paymentStatus(PaymentStatus.PENDING)
                .user(createUser())
                .restaurant(createRestaurant())
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

    public static ReservationMenuItem createReservationMenuItem() {
        return ReservationMenuItem.builder()
                .reservation(createReservation())
                .menuItem(createMenuItem())
                .quantity(5)
                .build();
    }

    public static Review createReview(){
        return Review.builder()
                .rating(4)
                .description("Great")
                .user(createUser())
                .restaurant(createRestaurant())
                .time(Instant.now())
                .build();
    }

    public static Event createEvent(){
        return Event.builder()
                .name("Some event")
                .description("Some description")
                .restaurant(createRestaurant())
                .build();
    }
}
