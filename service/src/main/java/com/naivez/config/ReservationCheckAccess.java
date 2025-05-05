package com.naivez.config;

import com.naivez.entity.Reservation;
import com.naivez.entity.User;
import com.naivez.entity.enums.Role;
import com.naivez.repository.ReservationRepository;
import com.naivez.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("reservationCheckAccess")
@RequiredArgsConstructor
public class ReservationCheckAccess {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public boolean hasAccessToReservation(Long reservationId, Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        return currentUser.getRole() == Role.ADMIN || reservation.getUser().getId().equals(currentUser.getId());
    }
}