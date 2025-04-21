package com.naivez.dto.user;

import com.naivez.dto.reservation.ReservationReadDto;
import com.naivez.entity.enums.Role;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserReadDto {

    Long id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    Role role;
    Boolean isBlacklisted;
    String blacklistReason;
    List<ReservationReadDto> reservations;
}
