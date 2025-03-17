package com.naivez.repository;

import com.naivez.entity.ReservationMenuItem;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationMenuItemRepository extends RepositoryBase<Long, ReservationMenuItem>{
    public ReservationMenuItemRepository(EntityManager entityManager) {
        super(ReservationMenuItem.class, entityManager);
    }
}
