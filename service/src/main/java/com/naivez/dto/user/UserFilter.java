package com.naivez.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class UserFilter {

    String firstName;

    String lastName;

    String email;

    String phoneNumber;

    Boolean isBlacklisted;
}
