package com.naivez.service;

import com.naivez.dto.reservation.ReservationReadDto;
import com.naivez.entity.Reservation;
import com.naivez.entity.User;
import com.naivez.mapper.reservation.ReservationReadMapper;
import com.naivez.repository.ReservationRepository;
import com.naivez.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationReadMapper reservationReadMapper;
    private final UserRepository userRepository;

    public List<ReservationReadDto> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservationReadMapper::toDto)
                .toList();
    }

    public Optional<ReservationReadDto> findById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationReadMapper::toDto);
    }

    @Transactional
    public boolean cancelReservation(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            return reservationRepository.findById(id)
                    .map(entity -> {
                        reservationRepository.delete(entity);
                        reservationRepository.flush();
                        return true;
                    })
                    .orElse(false);
        }

        return reservationRepository.findById(id)
                .filter(reservation -> reservation.getUser().getEmail().equals(currentUserEmail))
                .map(entity -> {
                    reservationRepository.delete(entity);
                    reservationRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public boolean hasAccessToReservation(Long reservationId, Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        return reservation.getUser().getId().equals(currentUser.getId()) || currentUser.getRole().getAuthority().equals("ADMIN");
    }


}
