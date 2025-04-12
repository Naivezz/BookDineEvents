package com.naivez.repository;

import com.naivez.entity.Reservation;
import com.naivez.entity.ReservationMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationMenuItemRepository extends JpaRepository<ReservationMenuItem, Long> {
}
