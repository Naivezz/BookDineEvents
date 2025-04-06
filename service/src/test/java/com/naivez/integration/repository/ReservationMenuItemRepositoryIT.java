package com.naivez.integration.repository;

import com.naivez.entity.ReservationMenuItem;
import com.naivez.repository.MenuItemRepository;
import com.naivez.repository.ReservationMenuItemRepository;
import com.naivez.repository.ReservationRepository;
import com.naivez.repository.RestaurantRepository;
import com.naivez.repository.UserRepository;
import com.naivez.util.DataBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ReservationMenuItemRepositoryIT extends IntegrationTestBase {

    private final ReservationMenuItemRepository reservationMenuItemRepository;
    private final ReservationRepository reservationRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Test
    void saveReservationMenuItem() {
        var user = DataBuilder.createUser();
        userRepository.save(user);
        var restaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(restaurant);
        var reservation = DataBuilder.createReservation();
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        reservationRepository.save(reservation);
        menuItemRepository.save(menuItem);
        var reservationMenuItem = DataBuilder.createReservationMenuItem();
        reservationMenuItem.setReservation(reservation);
        reservationMenuItem.setMenuItem(menuItem);

        reservationMenuItemRepository.save(reservationMenuItem);

        assertThat(reservationMenuItemRepository.findById(reservationMenuItem.getId())).isPresent();
    }

    @Test
    void deleteReservationMenuItem() {
        var user = DataBuilder.createUser();
        userRepository.save(user);
        var restaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(restaurant);
        var reservation = DataBuilder.createReservation();
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        reservationRepository.save(reservation);
        menuItemRepository.save(menuItem);
        var reservationMenuItem = DataBuilder.createReservationMenuItem();
        reservationMenuItem.setReservation(reservation);
        reservationMenuItem.setMenuItem(menuItem);
        reservationMenuItemRepository.save(reservationMenuItem);

        reservationMenuItemRepository.delete(reservationMenuItem);

        assertThat(reservationMenuItemRepository.findById(reservationMenuItem.getId())).isEmpty();
    }

    @Test
    void updateReservationMenuItem() {
        var user = DataBuilder.createUser();
        userRepository.save(user);
        var restaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(restaurant);
        var reservation = DataBuilder.createReservation();
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        reservationRepository.save(reservation);
        menuItemRepository.save(menuItem);
        var reservationMenuItem = DataBuilder.createReservationMenuItem();
        reservationMenuItem.setReservation(reservation);
        reservationMenuItem.setMenuItem(menuItem);
        reservationMenuItemRepository.save(reservationMenuItem);

        reservationMenuItem.setQuantity(3);
        reservationMenuItemRepository.save(reservationMenuItem);

        assertThat(reservationMenuItemRepository.findById(reservationMenuItem.getId()))
                .map(ReservationMenuItem::getQuantity)
                .contains(3);
    }

    @Test
    void findByIdReservationMenuItem() {
        var user = DataBuilder.createUser();
        userRepository.save(user);
        var restaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(restaurant);
        var reservation = DataBuilder.createReservation();
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);
        var menuItem = DataBuilder.createMenuItem();
        menuItem.setRestaurant(restaurant);
        reservationRepository.save(reservation);
        menuItemRepository.save(menuItem);
        var reservationMenuItem = DataBuilder.createReservationMenuItem();
        reservationMenuItem.setReservation(reservation);
        reservationMenuItem.setMenuItem(menuItem);
        reservationMenuItemRepository.save(reservationMenuItem);

        var expectedReservationMenuItem = reservationMenuItemRepository.findById(reservationMenuItem.getId());

        assertThat(expectedReservationMenuItem).isPresent();
        assertThat(expectedReservationMenuItem.get().getId()).isEqualTo(reservationMenuItem.getId());
    }

    @Test
    void findAllReservationMenuItems() {
        var user = DataBuilder.createUser();
        userRepository.save(user);
        var restaurant = DataBuilder.createRestaurant();
        restaurantRepository.save(restaurant);
        var reservation = DataBuilder.createReservation();
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);
        var menuItem1 = DataBuilder.createMenuItem();
        menuItem1.setRestaurant(restaurant);
        var menuItem2 = DataBuilder.createMenuItem();
        menuItem2.setRestaurant(restaurant);
        reservationRepository.save(reservation);
        menuItemRepository.save(menuItem1);
        menuItemRepository.save(menuItem2);
        var reservationMenuItem1 = DataBuilder.createReservationMenuItem();
        reservationMenuItem1.setReservation(reservation);
        reservationMenuItem1.setMenuItem(menuItem1);
        var reservationMenuItem2 = DataBuilder.createReservationMenuItem();
        reservationMenuItem2.setReservation(reservation);
        reservationMenuItem2.setMenuItem(menuItem2);
        reservationMenuItemRepository.save(reservationMenuItem1);
        reservationMenuItemRepository.save(reservationMenuItem2);

        var reservationMenuItems = reservationMenuItemRepository.findAll();

        assertThat(reservationMenuItems).hasSize(2);
    }
}

