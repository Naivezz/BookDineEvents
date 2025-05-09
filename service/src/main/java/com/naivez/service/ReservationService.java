package com.naivez.service;

import com.naivez.dto.reservation.ReservationReadDto;
import com.naivez.entity.enums.Role;
import com.naivez.mapper.reservation.ReservationReadMapper;
import com.naivez.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.getAuthority()));

        return reservationRepository.findById(id)
                .filter(entity -> isAdmin || entity.getUser().getEmail().equals(currentUserEmail))
                .map(entity -> {
                    reservationRepository.delete(entity);
                    reservationRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
