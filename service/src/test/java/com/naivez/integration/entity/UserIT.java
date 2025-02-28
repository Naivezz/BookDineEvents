package com.naivez.integration.entity;

import com.naivez.entity.User;
import com.naivez.entity.enums.Role;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserIT extends IntegrationTestBase {


    @Test
    void addUser() {
        Session session = getSession();

        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();
        session.persist(user);
        session.clear();

        var actualUser = session.get(User.class, user.getId());

        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getId()).isNotNull();
        assertEquals("Test firstname", actualUser.getFirstName());
    }

    @Test
    void getUser() {
        Session session = getSession();

        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();
        session.persist(user);
        session.clear();

        var actualUser = session.get(User.class, user.getId());
        assertEquals(user, actualUser);
    }

    @Test
    void updateUser() {
        Session session = getSession();

        var user = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("some password")
                .phoneNumber("+26262392")
                .role(Role.USER)
                .build();
        session.persist(user);
        session.clear();

        user.setRole(Role.ADMIN);
        user.setEmail("update@gmail.com");
        session.merge(user);
        session.flush();
        session.clear();

        var updatedUser = session.get(User.class, user.getId());

        assertEquals(Role.ADMIN, updatedUser.getRole());
        assertEquals("update@gmail.com", updatedUser.getEmail());
    }

    @Test
    void deleteUser() {
        Session session = getSession();

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
        session.clear();

        User managedUser = session.get(User.class, user.getId());
        assertThat(managedUser).isNotNull();

        session.remove(managedUser);
        session.flush();
        session.clear();

        assertNull(session.get(User.class, user.getId()));
    }

}
