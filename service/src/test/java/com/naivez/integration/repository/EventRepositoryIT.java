package com.naivez.integration.repository;

import com.naivez.annotation.IT;
import com.naivez.entity.Event;
import com.naivez.repository.EventRepository;
import com.naivez.repository.RestaurantRepository;
import com.naivez.util.DataBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class EventRepositoryIT{

    private final EventRepository eventRepository;
    private final RestaurantRepository restaurantRepository;

    @Test
    void saveEvent() {
        var restaurant = DataBuilder.createRestaurant();
        var event = DataBuilder.createEvent();

        restaurantRepository.save(restaurant);
        eventRepository.save(event);

        assertThat(eventRepository.findById(event.getId())).isPresent();
    }

    @Test
    void deleteEvent() {
        var restaurant = DataBuilder.createRestaurant();
        var event = DataBuilder.createEvent();
        restaurantRepository.save(restaurant);
        eventRepository.save(event);

        eventRepository.delete(event);

        assertThat(eventRepository.findById(event.getId())).isEmpty();
    }

    @Test
    void updateEvent() {
        var restaurant = DataBuilder.createRestaurant();
        var event = DataBuilder.createEvent();
        restaurantRepository.save(restaurant);
        eventRepository.save(event);

        event.setName("Updated Event");
        eventRepository.update(event);

        assertThat(eventRepository.findById(event.getId()))
                .map(Event::getName)
                .contains("Updated Event");
    }

    @Test
    void findByIdEvent() {
        var restaurant = DataBuilder.createRestaurant();
        var event = DataBuilder.createEvent();
        restaurantRepository.save(restaurant);
        eventRepository.save(event);

        var expectedEvent = eventRepository.findById(event.getId());

        assertThat(expectedEvent).isPresent();
        assertThat(expectedEvent.get().getId()).isEqualTo(event.getId());
    }

    @Test
    void findAllEvents() {
        var restaurant = DataBuilder.createRestaurant();
        var event1 = DataBuilder.createEvent();
        var event2 = DataBuilder.createEvent();
        restaurantRepository.save(restaurant);
        event1.setRestaurant(restaurant);
        event2.setRestaurant(restaurant);
        eventRepository.save(event1);
        eventRepository.save(event2);

        var events = eventRepository.findAll();

        assertThat(events).hasSize(2);
    }
}
