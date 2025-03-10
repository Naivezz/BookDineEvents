package com.naivez.dao;

import com.naivez.entity.Event;
import jakarta.persistence.EntityManager;

public class EventRepository extends RepositoryBase<Long, Event>{

    public EventRepository(Class<Event> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
