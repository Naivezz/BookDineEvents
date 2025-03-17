package com.naivez.integration.repository;

import com.naivez.entity.ReservationMenuItem;
import com.naivez.integration.IntegrationTestBase;
import com.naivez.repository.MenuItemRepository;
import com.naivez.repository.ReservationMenuItemRepository;
import com.naivez.repository.ReservationRepository;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationMenuItemRepositoryIT extends IntegrationTestBase {

    private ReservationMenuItemRepository reservationMenuItemRepository = context.getBean(ReservationMenuItemRepository.class);
    private ReservationRepository reservationRepository = context.getBean(ReservationRepository.class);
    private MenuItemRepository menuItemRepository = context.getBean(MenuItemRepository.class);

    @Test
    void saveReservationMenuItem() {
        var reservation = DataBuilder.createReservation();
        var menuItem = DataBuilder.createMenuItem();
        var reservationMenuItem = new ReservationMenuItem();
        reservationRepository.save(reservation);
        menuItemRepository.save(menuItem);
        reservationMenuItem.setReservation(reservation);
        reservationMenuItem.setMenuItem(menuItem);

        reservationMenuItemRepository.save(reservationMenuItem);

        assertThat(reservationMenuItemRepository.findById(reservationMenuItem.getId())).isPresent();
    }

    @Test
    void deleteReservationMenuItem() {
        var reservation = DataBuilder.createReservation();
        var menuItem = DataBuilder.createMenuItem();
        var reservationMenuItem = new ReservationMenuItem();
        reservationRepository.save(reservation);
        menuItemRepository.save(menuItem);
        reservationMenuItem.setReservation(reservation);
        reservationMenuItem.setMenuItem(menuItem);
        reservationMenuItemRepository.save(reservationMenuItem);

        reservationMenuItemRepository.delete(reservationMenuItem);

        assertThat(reservationMenuItemRepository.findById(reservationMenuItem.getId())).isEmpty();
    }


    @Test
    void updateReservationMenuItem() {
        var reservation = DataBuilder.createReservation();
        var menuItem = DataBuilder.createMenuItem();
        var reservationMenuItem = new ReservationMenuItem();
        reservationRepository.save(reservation);
        menuItemRepository.save(menuItem);
        reservationMenuItem.setReservation(reservation);
        reservationMenuItem.setMenuItem(menuItem);
        reservationMenuItemRepository.save(reservationMenuItem);

        reservationMenuItem.setQuantity(3);
        reservationMenuItemRepository.update(reservationMenuItem);

        assertThat(reservationMenuItemRepository.findById(reservationMenuItem.getId()))
                .map(ReservationMenuItem::getQuantity)
                .contains(3);
    }

    @Test
    void findByIdReservationMenuItem() {
        var reservation = DataBuilder.createReservation();
        var menuItem = DataBuilder.createMenuItem();
        var reservationMenuItem = new ReservationMenuItem();
        reservationRepository.save(reservation);
        menuItemRepository.save(menuItem);
        reservationMenuItem.setReservation(reservation);
        reservationMenuItem.setMenuItem(menuItem);
        reservationMenuItemRepository.save(reservationMenuItem);

        var expectedReservationMenuItem = reservationMenuItemRepository.findById(reservationMenuItem.getId());

        assertThat(expectedReservationMenuItem).isPresent();
        assertThat(expectedReservationMenuItem.get().getId()).isEqualTo(reservationMenuItem.getId());
    }

    @Test
    void findAllReservationMenuItems() {
        var reservation = DataBuilder.createReservation();
        var menuItem1 = DataBuilder.createMenuItem();
        var menuItem2 = DataBuilder.createMenuItem();
        reservationRepository.save(reservation);
        menuItemRepository.save(menuItem1);
        menuItemRepository.save(menuItem2);
        var reservationMenuItem1 = new ReservationMenuItem();
        reservationMenuItem1.setReservation(reservation);
        reservationMenuItem1.setMenuItem(menuItem1);
        var reservationMenuItem2 = new ReservationMenuItem();
        reservationMenuItem2.setReservation(reservation);
        reservationMenuItem2.setMenuItem(menuItem2);
        reservationMenuItemRepository.save(reservationMenuItem1);
        reservationMenuItemRepository.save(reservationMenuItem2);

        var reservationMenuItems = reservationMenuItemRepository.findAll();

        assertThat(reservationMenuItems).hasSize(2);
    }

}
