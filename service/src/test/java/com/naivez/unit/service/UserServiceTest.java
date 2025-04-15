package com.naivez.unit.service;

import com.naivez.dto.user.UserCreateEditDto;
import com.naivez.dto.user.UserReadDto;
import com.naivez.entity.User;
import com.naivez.mapper.user.UserCreateEditMapper;
import com.naivez.mapper.user.UserReadMapper;
import com.naivez.repository.UserRepository;
import com.naivez.service.UserService;
import com.naivez.util.UserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Long USER_ID = 1L;
    private static final Long INVALID_USER_ID = 123L;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserReadMapper userReadMapper;

    @Mock
    private UserCreateEditMapper userCreateEditMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {
        UserCreateEditDto userCreateEditDto = UserTestData.buildUserCreateEditDto();
        User user = UserTestData.buildUser();
        UserReadDto userReadDto = UserTestData.buildUserReadDto();
        doReturn(user).when(userCreateEditMapper).toEntity(userCreateEditDto);
        doReturn(user).when(userRepository).save(user);
        doReturn(userReadDto).when(userReadMapper).toDto(user);

        UserReadDto result = userService.create(userCreateEditDto);

        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
        verify(userReadMapper, times(1)).toDto(user);
    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = UserTestData.buildUser();
        User user2 = UserTestData.buildUpdatedUser();
        UserReadDto userReadDto1 = UserTestData.buildUserReadDto();
        UserReadDto userReadDto2 = UserTestData.buildUpdatedUserReadDto();
        doReturn(List.of(user1, user2)).when(userRepository).findAll();
        doReturn(userReadDto1).when(userReadMapper).toDto(user1);
        doReturn(userReadDto2).when(userReadMapper).toDto(user2);

        var result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
        verify(userReadMapper, times(1)).toDto(user1);
        verify(userReadMapper, times(1)).toDto(user2);
    }

    @Test
    void shouldReturnUserByIdWhenUserExists() {
        User user = UserTestData.buildUser();
        UserReadDto userReadDto = UserTestData.buildUserReadDto();
        doReturn(Optional.of(user)).when(userRepository).findById(USER_ID);
        doReturn(userReadDto).when(userReadMapper).toDto(user);

        Optional<UserReadDto> result = userService.findById(USER_ID);

        assertTrue(result.isPresent());
        assertEquals(userReadDto, result.get());
        verify(userRepository, times(1)).findById(USER_ID);
        verify(userReadMapper, times(1)).toDto(user);
    }

    @Test
    void shouldReturnEmptyWhenUserByIdNotFound() {
        doReturn(Optional.empty()).when(userRepository).findById(INVALID_USER_ID);

        Optional<UserReadDto> result = userService.findById(INVALID_USER_ID);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(INVALID_USER_ID);
        verify(userReadMapper, never()).toDto(any());
    }

    @Test
    void shouldUpdateUserWhenUserExists() {
        UserCreateEditDto userCreateEditDto = UserTestData.buildUserCreateEditDto();
        User existingUser = UserTestData.buildUser();
        User updatedUser = UserTestData.buildUpdatedUser();
        UserReadDto updatedUserReadDto = UserTestData.buildUpdatedUserReadDto();
        doReturn(Optional.of(existingUser)).when(userRepository).findById(USER_ID);
        doNothing().when(userCreateEditMapper).toEntity(userCreateEditDto, existingUser);
        doReturn(updatedUser).when(userRepository).save(existingUser);
        doReturn(updatedUserReadDto).when(userReadMapper).toDto(updatedUser);

        Optional<UserReadDto> result = userService.update(USER_ID, userCreateEditDto);

        assertTrue(result.isPresent());
        assertEquals("Updated firstName dto", result.get().getFirstName());
        verify(userRepository, times(1)).findById(USER_ID);
        verify(userRepository, times(1)).save(existingUser);
        verify(userReadMapper, times(1)).toDto(updatedUser);
    }

    @Test
    void shouldReturnEmptyWhenUpdatingNonExistentUser() {
        UserCreateEditDto userCreateEditDto = UserTestData.buildUserCreateEditDto();
        doReturn(Optional.empty()).when(userRepository).findById(INVALID_USER_ID);

        Optional<UserReadDto> result = userService.update(INVALID_USER_ID, userCreateEditDto);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(INVALID_USER_ID);
        verify(userRepository, never()).save(any());
        verify(userReadMapper, never()).toDto(any());
    }

    @Test
    void shouldDeleteUserWhenUserExists() {
        User user = UserTestData.buildUser();
        doReturn(Optional.of(user)).when(userRepository).findById(USER_ID);
        doNothing().when(userRepository).delete(user);

        boolean result = userService.delete(USER_ID);

        assertTrue(result);
        verify(userRepository, times(1)).findById(USER_ID);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistentUser() {
        doReturn(Optional.empty()).when(userRepository).findById(INVALID_USER_ID);

        boolean result = userService.delete(INVALID_USER_ID);

        assertFalse(result);
        verify(userRepository, times(1)).findById(INVALID_USER_ID);
        verify(userRepository, never()).delete(any());
    }
}
