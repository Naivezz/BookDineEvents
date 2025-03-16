package com.naivez.integration.entity;

import com.naivez.entity.Event;
import com.naivez.entity.Restaurant;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EventIT extends IntegrationTestBase {

    @Test
    void addEvent() {
        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(restaurant);
        session.flush();

        var event = Event.builder()
                .name("Some event")
                .description("Some description")
                .restaurant(restaurant)
                .build();

        session.persist(event);
        session.flush();
        session.clear();

        var savedEvent = session.get(Event.class, event.getId());

        assertThat(savedEvent).isNotNull();
        assertThat(savedEvent.getId()).isNotNull();
        assertEquals("Some event", savedEvent.getName());
        assertEquals("Some description", savedEvent.getDescription());
        assertEquals(restaurant, savedEvent.getRestaurant());
    }

    @Test
    void getEvent() {
        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(restaurant);
        session.flush();

        var event = Event.builder()
                .name("Some event")
                .description("Some description")
                .restaurant(restaurant)
                .build();

        session.persist(event);
        session.flush();
        session.clear();

        var managedEvent = session.get(Event.class, event.getId());

        assertEquals(event, managedEvent);
    }

    @Test
    void updateEvent() {
        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(restaurant);
        session.flush();

        var event = Event.builder()
                .name("Some event")
                .description("Some description")
                .restaurant(restaurant)
                .build();

        session.persist(event);
        session.flush();
        session.clear();

        event.setName("Update name");
        event.setDescription("Update description");
        session.merge(event);
        session.flush();
        session.clear();

        var updatedEvent = session.get(Event.class, event.getId());

        assertEquals("Update name", updatedEvent.getName());
        assertEquals("Update description", updatedEvent.getDescription());
    }

    @Test
    void deleteEvent() {
        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(restaurant);
        session.flush();

        var event = Event.builder()
                .name("Some event")
                .description("Some description")
                .restaurant(restaurant)
                .build();

        session.persist(event);
        session.flush();
        session.clear();

        var managedEvent = session.get(Event.class, event.getId());
        assertThat(managedEvent).isNotNull();

        session.remove(managedEvent);
        session.flush();
        session.clear();

        assertNull(session.get(Event.class, event.getId()));
    }
}
