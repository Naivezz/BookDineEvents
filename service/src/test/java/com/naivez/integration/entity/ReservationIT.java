package com.naivez.integration.entity;

import com.naivez.entity.Reservation;
import com.naivez.entity.Spot;
import com.naivez.entity.User;
import com.naivez.entity.enums.ReservationStatus;
import com.naivez.entity.enums.Role;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReservationIT extends IntegrationTestBase {

    @Test
    void addReservation() {
        Session session = getSession();

        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();

        var spot = Spot.builder()
                .tableNumber(5)
                .seats(4)
                .build();

        session.persist(user);
        session.persist(spot);
        session.flush();

        var reservation = Reservation.builder()
                .time(LocalDateTime.now().plusDays(1))
                .guests(4)
                .status(ReservationStatus.CONFIRMED)
                .user(user)
                .spot(spot)
                .build();
        session.persist(reservation);
        session.clear();

        var actualReservation = session.get(Reservation.class, reservation.getId());

        assertThat(actualReservation).isNotNull();
        assertThat(actualReservation.getId()).isNotNull();
        assertEquals(ReservationStatus.CONFIRMED, actualReservation.getStatus());
    }

    @Test
    void getReservation() {
        Session session = getSession();

        var reservation = Reservation.builder()
                .guests(2)
                .status(ReservationStatus.PENDING)
                .build();
        session.persist(reservation);
        session.clear();

        var actualReservation = session.getReference(Reservation.class, reservation.getId());
        assertEquals(reservation, actualReservation);
    }

    @Test
    void updateReservation() {
        Session session = getSession();

        var reservation = Reservation.builder()
                .time(LocalDateTime.now().plusDays(5))
                .guests(3)
                .status(ReservationStatus.PENDING)
                .build();
        session.persist(reservation);
        session.clear();

        reservation.setStatus(ReservationStatus.CANCELLED);
        session.merge(reservation);
        session.flush();
        session.clear();

        var updatedReservation = session.get(Reservation.class, reservation.getId());

        assertEquals(ReservationStatus.CANCELLED, updatedReservation.getStatus());
    }

    @Test
    void deleteReservation() {
        Session session = getSession();

        var reservation = Reservation.builder()
                .time(LocalDateTime.now().plusDays(2))
                .guests(5)
                .status(ReservationStatus.CONFIRMED)
                .build();
        session.persist(reservation);
        session.flush();
        session.clear();

        Reservation managedReservation = session.get(Reservation.class, reservation.getId());
        assertThat(managedReservation).isNotNull();

        session.remove(managedReservation);
        session.flush();
        session.clear();

        assertNull(session.get(Reservation.class, reservation.getId()));
    }
}
