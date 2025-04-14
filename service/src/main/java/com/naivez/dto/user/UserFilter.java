package com.naivez.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {

    String firstName;

    String lastName;

    String email;

    String password;

    String phoneNumber;

    boolean isBlacklisted;
}
