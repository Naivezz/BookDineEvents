package com.naivez.integration.dao;

import com.naivez.dao.SpotRepository;
import com.naivez.entity.Spot;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SpotRepositoryIT extends IntegrationTestBase {

    private SpotRepository spotRepository;

    @BeforeEach
    void setUp() {
        spotRepository = new SpotRepository(Spot.class, session);
    }

    @Test
    void createSpot() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var testSpot = DataBuilder.createSpot();
        testSpot.setRestaurant(restaurant);

        spotRepository.save(testSpot);
        session.clear();

        assertThat(session.get(Spot.class, testSpot.getId())).isNotNull();
    }

    @Test
    void updateSpot() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var testSpot = DataBuilder.createSpot();
        testSpot.setRestaurant(restaurant);
        spotRepository.save(testSpot);
        session.clear();

        testSpot.setTableNumber(13);
        spotRepository.update(testSpot);
        session.flush();
        session.clear();
        Spot updatedSpot = session.get(Spot.class, testSpot.getId());

        assertThat(updatedSpot).isEqualTo(testSpot);
    }

    @Test
    void findSpotById() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var testSpot = DataBuilder.createSpot();
        testSpot.setRestaurant(restaurant);
        spotRepository.save(testSpot);
        session.clear();

        var expectedSpot = spotRepository.findById(testSpot.getId());

        assertThat(expectedSpot).isEqualTo(Optional.of(testSpot));
    }

    @Test
    void findAllSpots() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var testSpot1 = DataBuilder.createSpot();
        testSpot1.setRestaurant(restaurant);
        spotRepository.save(testSpot1);
        session.flush();
        var testSpot2 = DataBuilder.createSpot();
        testSpot2.setRestaurant(restaurant);
        spotRepository.save(testSpot2);
        session.flush();
        session.clear();

        List<Spot> spots = spotRepository.findAll();

        assertThat(spots)
                .isNotEmpty()
                .containsExactlyInAnyOrder(testSpot1, testSpot2);
    }

    @Test
    void deleteSpot() {
        var restaurant = DataBuilder.createRestaurant();
        session.persist(restaurant);
        var testSpot = DataBuilder.createSpot();
        testSpot.setRestaurant(restaurant);
        spotRepository.save(testSpot);
        session.clear();

        spotRepository.delete(testSpot.getId());

        assertThat(session.get(Spot.class, testSpot.getId())).isNull();
    }
}
