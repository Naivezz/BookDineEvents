package com.naivez.integration.entity;

import com.naivez.entity.BlackList;
import com.naivez.entity.Restaurant;
import com.naivez.entity.User;
import com.naivez.entity.enums.Role;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BlackListIT extends IntegrationTestBase {

    @Test
    void addBlackList() {
        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();

        session.persist(user);
        session.flush();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(restaurant);
        session.flush();

        var blackList = BlackList.builder()
                .reason("Some reason")
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(blackList);
        session.flush();
        session.clear();

        var savedBlackList = session.get(BlackList.class, blackList.getId());

        assertThat(savedBlackList).isNotNull();
        assertThat(savedBlackList.getId()).isNotNull();
        assertEquals("Some reason", savedBlackList.getReason());
        assertEquals(user, savedBlackList.getUser());
        assertEquals(restaurant, savedBlackList.getRestaurant());
    }

    @Test
    void getBlackList() {
        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();

        session.persist(user);
        session.flush();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(restaurant);
        session.flush();

        var blackList = BlackList.builder()
                .reason("Some reason")
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(blackList);
        session.flush();
        session.clear();

        var retrievedBlackList = session.get(BlackList.class, blackList.getId());

        assertEquals(blackList, retrievedBlackList);
    }

    @Test
    void updateBlackList() {
        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();

        session.persist(user);
        session.flush();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(restaurant);
        session.flush();

        var blackList = BlackList.builder()
                .reason("Some reason")
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(blackList);
        session.flush();
        session.clear();

        blackList.setReason("Update reason");
        session.merge(blackList);
        session.flush();
        session.clear();

        var updatedBlackList = session.get(BlackList.class, blackList.getId());

        assertEquals("Update reason", updatedBlackList.getReason());
    }

    @Test
    void deleteBlackList() {
        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();

        session.persist(user);
        session.flush();

        var restaurant = Restaurant.builder()
                .name("Test name")
                .address("Test address")
                .phoneNumber("+63463733")
                .build();

        session.persist(restaurant);
        session.flush();

        var blackList = BlackList.builder()
                .reason("Some reason")
                .user(user)
                .restaurant(restaurant)
                .build();

        session.persist(blackList);
        session.flush();
        session.clear();

        var managedBlackList = session.get(BlackList.class, blackList.getId());
        assertThat(managedBlackList).isNotNull();

        session.remove(managedBlackList);
        session.flush();
        session.clear();

        assertNull(session.get(BlackList.class, blackList.getId()));
    }
}
