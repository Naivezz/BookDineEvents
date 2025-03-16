package com.naivez.integration.dao;

import com.naivez.repository.UserDao;
import com.naivez.dto.UserDto;
import com.naivez.entity.User;
import com.naivez.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UserFilterIT extends IntegrationTestBase {
    private final UserDao userDao = UserDao.getInstance();

    @Test
    void shouldReturnAllUsersByFirstName() {
        var expectedUser1 = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("password")
                .phoneNumber("+48678249294")
                .build();
        var expectedUser2 = User.builder()
                .firstName("Test firstname")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("pass")
                .phoneNumber("+48676369294")
                .build();
        session.persist(expectedUser1);
        session.persist(expectedUser2);
        session.clear();
        var filter = UserDto.builder()
                .firstName(expectedUser1.getFirstName())
                .build();


        var listOfUsers = userDao.findAllByFirstname(session, filter);

        assertThat(listOfUsers.size()).isEqualTo(2);
        assertThat(listOfUsers.get(0)).isEqualTo(expectedUser1);
        assertThat(listOfUsers.get(1)).isEqualTo(expectedUser2);
    }

    @Test
    void shouldReturnUsersByFirstNameAndLastName() {
        var expectedUser1 = User.builder()
                .firstName("test firstname")
                .lastName("test lastname")
                .email("test@gmail.com")
                .password("password")
                .phoneNumber("+48678249294")
                .build();
        var expectedUser2 = User.builder()
                .firstName("another firstname")
                .lastName("another lastname")
                .email("test@gmail.com")
                .password("pass")
                .phoneNumber("+48676369294")
                .build();
        session.persist(expectedUser1);
        session.persist(expectedUser2);
        session.clear();
        var filter = UserDto.builder()
                .firstName(expectedUser1.getFirstName())
                .lastName(expectedUser1.getLastName())
                .build();

        var listOfUsers = userDao.findByFirstnameAndLastName(session, filter);

        assertThat(listOfUsers.size()).isEqualTo(1);
        assertThat(listOfUsers.get(0)).isEqualTo(expectedUser1);
        assertThat(listOfUsers).doesNotContain(expectedUser2);
    }

    @Test
    void shouldReturnUsersByAnyField() {
        var expectedUser1 = User.builder()
                .firstName("test firstname")
                .lastName("test lastname")
                .email("test@gmail.com")
                .password("password")
                .phoneNumber("+48678249294")
                .build();
        var expectedUser2 = User.builder()
                .firstName("another firstname")
                .lastName("another lastname")
                .email("test@gmail.com")
                .password("pass")
                .phoneNumber("+48676369294")
                .build();
        session.persist(expectedUser1);
        session.persist(expectedUser2);
        session.clear();
        var filter = UserDto.builder()
                .firstName(expectedUser1.getFirstName())
                .lastName(expectedUser2.getLastName())
                .build();

        var listOfUsers = userDao.findByAnyField(session, filter);

        assertThat(listOfUsers).hasSize(2).containsExactlyInAnyOrder(expectedUser1, expectedUser2);
    }

    @Test
    void shouldReturnUsersByFirstNameAndEmail() {
        var expectedUser1 = User.builder()
                .firstName("test firstname")
                .lastName("test lastname")
                .email("test@gmail.com")
                .password("password")
                .phoneNumber("+48678249294")
                .build();
        var expectedUser2 = User.builder()
                .firstName("test firstname")
                .lastName("another lastname")
                .email("test@gmail.com")
                .password("pass")
                .phoneNumber("+48676369294")
                .build();
        session.persist(expectedUser1);
        session.persist(expectedUser2);
        session.clear();
        var filter = UserDto.builder()
                .firstName(expectedUser1.getFirstName())
                .email(expectedUser2.getEmail())
                .build();

        var listOfUsers = userDao.findByFirstNameAndEmail(session, filter);

        assertThat(listOfUsers).hasSize(2).containsExactlyInAnyOrder(expectedUser1, expectedUser2);
    }
}
