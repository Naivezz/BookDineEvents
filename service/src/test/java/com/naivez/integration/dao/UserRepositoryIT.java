package com.naivez.integration.dao;

import com.naivez.dao.UserRepository;
import com.naivez.entity.User;
import com.naivez.entity.enums.Role;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryIT extends IntegrationTestBase {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository(User.class, session);
    }

    @Test
    void createUser() {
        var testUser = DataBuilder.createUser();
        userRepository.save(testUser);
        session.clear();

        assertThat(session.get(User.class, testUser.getId())).isNotNull();
    }

    @Test
    void updateUser() {
        var testUser = DataBuilder.createUser();
        userRepository.save(testUser);
        session.clear();
        testUser.setRole(Role.ADMIN);

        userRepository.update(testUser);
        session.flush();
        session.clear();
        User updatedUser = session.get(User.class, testUser.getId());

        assertThat(updatedUser).isEqualTo(testUser);
    }


    @Test
    void findUserById() {
        var testUser = DataBuilder.createUser();
        userRepository.save(testUser);
        session.clear();

        var expectedUser = userRepository.findById(testUser.getId());

        assertThat(expectedUser).isEqualTo(Optional.of(testUser));
    }

    @Test
    void findAllUsers() {
        var testUser1 = DataBuilder.createUser();
        userRepository.save(testUser1);
        session.flush();
        var testUser2 = DataBuilder.createUser();
        userRepository.save(testUser2);
        session.flush();
        session.clear();

        List<User> users = userRepository.findAll();

        assertThat(users)
                .isNotEmpty()
                .containsExactlyInAnyOrder(testUser1, testUser2);
    }


    @Test
    void deleteUser() {
        var testUser = DataBuilder.createUser();
        userRepository.save(testUser);

        userRepository.delete(testUser.getId());

        assertThat(session.get(User.class, testUser.getId())).isNull();
    }
}

