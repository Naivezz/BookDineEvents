package com.naivez.config;

import com.naivez.entity.enums.Role;
import com.naivez.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userCheckAccess")
@RequiredArgsConstructor
public class UserCheckAccess {

    private final UserRepository userRepository;

    public boolean isSelfOrAdmin(Long userId, Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .map(currentUser ->
                        currentUser.getId().equals(userId) ||
                        authentication.getAuthorities().stream()
                                .anyMatch(auth -> auth.getAuthority().equals(Role.ADMIN.name()))
                )
                .orElse(false);
    }

}
