package com.naivez.integration.dao;

import com.naivez.dao.EventRepository;
import com.naivez.entity.Event;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EventRepositoryIT extends IntegrationTestBase {

    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository = new EventRepository(Event.class, session);
    }

    @Test
    void createEvent() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        session.flush();
        var event = DataBuilder.createEvent();
        event.setRestaurant(restaurant);

        eventRepository.save(event);
        session.clear();

        assertThat(session.get(Event.class, event.getId())).isNotNull();
    }

    @Test
    void updateEvent() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        session.flush();
        var event = DataBuilder.createEvent();
        event.setRestaurant(restaurant);
        eventRepository.save(event);
        session.clear();

        event.setName("Updated Event Name");
        eventRepository.update(event);
        session.flush();
        session.clear();
        Event updatedEvent = session.get(Event.class, event.getId());

        assertThat(updatedEvent).isEqualTo(event);
    }

    @Test
    void findEventById() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        session.flush();
        var event = DataBuilder.createEvent();
        event.setRestaurant(restaurant);
        eventRepository.save(event);
        session.clear();

        var expectedEvent = eventRepository.findById(event.getId());

        assertThat(expectedEvent).isEqualTo(Optional.of(event));
    }

    @Test
    void findAllEvents() {
        var restaurant1 = DataBuilder.createRestaurant();
        session.persist(restaurant1);
        session.flush();
        var restaurant2 = DataBuilder.createRestaurant();
        session.persist(restaurant2);
        session.flush();
        var event1 = DataBuilder.createEvent();
        event1.setRestaurant(restaurant1);
        eventRepository.save(event1);
        session.flush();
        var event2 = DataBuilder.createEvent();
        event2.setRestaurant(restaurant2);
        eventRepository.save(event2);
        session.flush();
        session.clear();

        List<Event> events = eventRepository.findAll();

        assertThat(events)
                .isNotEmpty()
                .containsExactlyInAnyOrder(event1, event2);
    }

    @Test
    void deleteEvent() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        session.flush();
        var event = DataBuilder.createEvent();
        event.setRestaurant(restaurant);
        eventRepository.save(event);
        session.clear();

        eventRepository.delete(event.getId());

        assertThat(session.get(Event.class, event.getId())).isNull();
    }
}
