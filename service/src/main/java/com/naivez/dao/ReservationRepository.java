package com.naivez.dao;

import com.naivez.entity.Reservation;
import jakarta.persistence.EntityManager;

public class ReservationRepository extends RepositoryBase<Long, Reservation>{

    public ReservationRepository(Class<Reservation> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
