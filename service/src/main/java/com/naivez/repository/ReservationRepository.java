package com.naivez.repository;

import com.naivez.entity.Reservation;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository extends RepositoryBase<Long, Reservation>{
    public ReservationRepository(EntityManager entityManager) {
        super(Reservation.class, entityManager);
    }
}
