package com.naivez.dto.user;

import com.naivez.entity.enums.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreateEditDto {

    String firstName;
    String lastName;
    String email;
    String password;
    String phoneNumber;
    Role role;
    boolean isBlacklisted;
    String blacklistReason;
}
