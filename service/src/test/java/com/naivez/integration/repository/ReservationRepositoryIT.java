package com.naivez.integration.repository;

import com.naivez.annotation.IT;
import com.naivez.entity.Reservation;
import com.naivez.repository.ReservationRepository;
import com.naivez.repository.RestaurantRepository;
import com.naivez.repository.UserRepository;
import com.naivez.util.DataBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class ReservationRepositoryIT {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Test
    void saveReservation() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        var reservation = DataBuilder.createReservation();

        restaurantRepository.save(restaurant);
        userRepository.save(user);
        reservationRepository.save(reservation);

        assertThat(reservationRepository.findById(reservation.getId())).isPresent();
    }

    @Test
    void deleteReservation() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        var reservation = DataBuilder.createReservation();
        restaurantRepository.save(restaurant);
        userRepository.save(user);
        reservation.setRestaurant(restaurant);
        reservation.setUser(user);
        reservationRepository.save(reservation);

        reservationRepository.delete(reservation);

        assertThat(reservationRepository.findById(reservation.getId())).isEmpty();
    }

    @Test
    void updateReservation() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        var reservation = DataBuilder.createReservation();
        restaurantRepository.save(restaurant);
        userRepository.save(user);
        reservation.setRestaurant(restaurant);
        reservation.setUser(user);
        reservationRepository.save(reservation);

        reservation.setGuests(6);
        reservationRepository.update(reservation);

        assertThat(reservationRepository.findById(reservation.getId()))
                .map(Reservation::getGuests)
                .contains(6);
    }

    @Test
    void findByIdReservation() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        var reservation = DataBuilder.createReservation();
        restaurantRepository.save(restaurant);
        userRepository.save(user);
        reservation.setRestaurant(restaurant);
        reservation.setUser(user);
        reservationRepository.save(reservation);

        var expectedReservation = reservationRepository.findById(reservation.getId());

        assertThat(expectedReservation).isPresent();
        assertThat(expectedReservation.get().getId()).isEqualTo(reservation.getId());
    }

    @Test
    void findAllReservations() {
        var restaurant = DataBuilder.createRestaurant();
        var user = DataBuilder.createUser();
        var reservation1 = DataBuilder.createReservation();
        var reservation2 = DataBuilder.createReservation();
        restaurantRepository.save(restaurant);
        userRepository.save(user);
        reservation1.setRestaurant(restaurant);
        reservation1.setUser(user);
        reservation2.setRestaurant(restaurant);
        reservation2.setUser(user);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        var reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(2);
    }
}
