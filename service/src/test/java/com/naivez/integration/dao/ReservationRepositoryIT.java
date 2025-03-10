package com.naivez.integration.dao;

import com.naivez.dao.ReservationRepository;
import com.naivez.entity.Reservation;
import com.naivez.entity.enums.ReservationStatus;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationRepositoryIT extends IntegrationTestBase {

    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(Reservation.class, session);
    }

    @Test
    void createReservation() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var spot = DataBuilder.createSpot();
        spot.setRestaurant(restaurant);
        session.persist(spot);
        var user = DataBuilder.createUser();
        session.persist(user);
        var testReservation = DataBuilder.createReservation();
        testReservation.setSpot(spot);
        testReservation.setUser(user);

        reservationRepository.save(testReservation);
        session.clear();

        assertThat(session.get(Reservation.class, testReservation.getId())).isNotNull();
    }

    @Test
    void updateReservation() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var spot = DataBuilder.createSpot();
        spot.setRestaurant(restaurant);
        session.persist(spot);
        var user = DataBuilder.createUser();
        session.persist(user);
        var testReservation = DataBuilder.createReservation();
        testReservation.setSpot(spot);
        testReservation.setUser(user);
        reservationRepository.save(testReservation);
        session.clear();

        testReservation.setStatus(ReservationStatus.MISSED);
        reservationRepository.update(testReservation);
        session.flush();
        session.clear();
        Reservation updatedReservation = session.get(Reservation.class, testReservation.getId());

        assertThat(updatedReservation).isEqualTo(testReservation);
    }

    @Test
    void findReservationById() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var spot = DataBuilder.createSpot();
        spot.setRestaurant(restaurant);
        session.persist(spot);
        var user = DataBuilder.createUser();
        session.persist(user);
        var testReservation = DataBuilder.createReservation();
        testReservation.setSpot(spot);
        testReservation.setUser(user);
        reservationRepository.save(testReservation);
        session.clear();

        var expectedReservation = reservationRepository.findById(testReservation.getId());

        assertThat(expectedReservation).isEqualTo(Optional.of(testReservation));
    }

    @Test
    void findAllReservations() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var spot = DataBuilder.createSpot();
        spot.setRestaurant(restaurant);
        session.persist(spot);
        var user = DataBuilder.createUser();
        session.persist(user);
        var testReservation1 = DataBuilder.createReservation();
        testReservation1.setSpot(spot);
        testReservation1.setUser(user);
        reservationRepository.save(testReservation1);
        session.flush();
        var testReservation2 = DataBuilder.createReservation();
        testReservation2.setSpot(spot);
        testReservation2.setUser(user);
        reservationRepository.save(testReservation2);
        session.flush();
        session.clear();

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations)
                .isNotEmpty()
                .containsExactlyInAnyOrder(testReservation1, testReservation2);
    }

    @Test
    void deleteReservation() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var spot = DataBuilder.createSpot();
        spot.setRestaurant(restaurant);
        session.persist(spot);
        var user = DataBuilder.createUser();
        session.persist(user);
        var testReservation = DataBuilder.createReservation();
        testReservation.setSpot(spot);
        testReservation.setUser(user);
        reservationRepository.save(testReservation);
        session.clear();

        reservationRepository.delete(testReservation.getId());

        assertThat(session.get(Reservation.class, testReservation.getId())).isNull();
    }
}
