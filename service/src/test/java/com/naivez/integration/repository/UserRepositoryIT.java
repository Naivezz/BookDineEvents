package com.naivez.integration.repository;

import com.naivez.entity.User;
import com.naivez.repository.UserRepository;
import com.naivez.util.DataBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class UserRepositoryIT extends IntegrationTestBase {

    private final UserRepository userRepository;
    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void saveUser() {
        var user = DataBuilder.createUser();

        userRepository.save(user);

        assertThat(userRepository.findById(user.getId())).isPresent();
    }

    @Test
    void deleteUser() {
        var user = DataBuilder.createUser();
        userRepository.save(user);

        userRepository.delete(user);

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void updateUser() {
        var user = DataBuilder.createUser();
        userRepository.save(user);

        user.setFirstName("Updated first name");
        userRepository.save(user);

        assertThat(userRepository.findById(user.getId()))
                .map(User::getFirstName)
                .contains("Updated first name");
    }

    @Test
    void findByIdUser() {
        var user = DataBuilder.createUser();
        userRepository.save(user);

        var expectedUser = userRepository.findById(user.getId());

        assertThat(expectedUser).isPresent();
        assertThat(expectedUser.get().getId()).isEqualTo(user.getId());
    }

    @Test
    void findAllUsers() {
        var user1 = DataBuilder.createUser();
        var user2 = DataBuilder.createUser();
        userRepository.save(user1);
        userRepository.save(user2);

        var users = userRepository.findAll();

        assertThat(users).hasSize(2);
    }
}
