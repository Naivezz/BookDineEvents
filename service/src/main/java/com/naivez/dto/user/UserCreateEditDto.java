package com.naivez.dto.user;

import com.naivez.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class UserCreateEditDto {

    @NotBlank(message = "First name is required")
    String firstName;

    @NotBlank(message = "Last name is required")
    String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100)
    String password;

    String phoneNumber;

    Role role;

    Boolean isBlacklisted;

    String blacklistReason;
}
