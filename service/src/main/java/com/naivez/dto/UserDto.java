package com.naivez.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    String firstName;

    String lastName;

    String email;

    String password;

    String phoneNumber;

    boolean isBlacklisted;
}
