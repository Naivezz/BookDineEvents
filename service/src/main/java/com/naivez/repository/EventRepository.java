package com.naivez.repository;

import com.naivez.entity.Event;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepository extends RepositoryBase<Long, Event>{
    public EventRepository(EntityManager entityManager) {
        super(Event.class, entityManager);
    }
}
