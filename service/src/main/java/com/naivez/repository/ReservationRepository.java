package com.naivez.repository;

import com.naivez.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @EntityGraph(attributePaths = {"restaurant"})
    @Query("SELECT r FROM Reservation r WHERE r.user.email = :email")
    List<Reservation> findByUserEmail(@Param("email") String email);
}
