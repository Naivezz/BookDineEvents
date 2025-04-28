package com.naivez.util;

import com.naivez.dto.user.UserCreateEditDto;
import com.naivez.dto.user.UserReadDto;
import com.naivez.entity.User;
import com.naivez.entity.enums.Role;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserTestData {

    public static UserCreateEditDto buildUserCreateEditDto() {
        return UserCreateEditDto.builder()
                .firstName("Test firstName")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("efef242r2")
                .phoneNumber("+425398284")
                .role(Role.USER)
                .blacklisted(false)
                .blacklistReason(null)
                .build();
    }

    public static User buildUser() {
        return User.builder()
                .id(1L)
                .firstName("Test firstName")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .password("efef242r2")
                .phoneNumber("+425398284")
                .role(Role.USER)
                .blacklisted(false)
                .blacklistReason(null)
                .build();
    }

    public static UserReadDto buildUserReadDto() {
        return UserReadDto.builder()
                .id(1L)
                .firstName("Test firstName")
                .lastName("Test lastname")
                .email("test@gmail.com")
                .phoneNumber("+425398284")
                .role(Role.USER)
                .isBlacklisted(false)
                .blacklistReason(null)
                .reservations(List.of())
                .build();
    }

    public static User buildUpdatedUser() {
        return User.builder()
                .id(1L)
                .firstName("Updated firstName")
                .lastName("Updated lastname")
                .email("updated@gmail.com")
                .password("fwegewg43")
                .phoneNumber("+4385839242")
                .role(Role.ADMIN)
                .blacklisted(true)
                .blacklistReason("Bad behavior")
                .build();
    }

    public static UserReadDto buildUpdatedUserReadDto() {
        return UserReadDto.builder()
                .id(1L)
                .firstName("Updated firstName dto")
                .lastName("Updated lastname dto")
                .email("updatedto@example.com")
                .phoneNumber("+429952922")
                .role(Role.ADMIN)
                .isBlacklisted(true)
                .blacklistReason("Bad behavior")
                .reservations(List.of())
                .build();
    }
}
