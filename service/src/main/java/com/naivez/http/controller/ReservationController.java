package com.naivez.http.controller;

import com.naivez.entity.enums.Role;
import com.naivez.repository.ReservationRepository;
import com.naivez.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    @GetMapping
    public String findAll(Model model, Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(Role.ADMIN.name()))) {
            model.addAttribute("reservations", reservationService.findAll());
            model.addAttribute("isAdmin", true);
        } else {
            String email = authentication.getName();
            model.addAttribute("reservations", reservationService.findByEmail(email));
            model.addAttribute("isAdmin", false);
        }
        return "reservation/reservations";
    }

    @GetMapping("/{id}")
    @PreAuthorize("@reservationCheckAccess.hasAccessToReservation(#id, authentication)")
    public String findById(@PathVariable("id") Long id, Model model) {
        return reservationService.findById(id)
                .map(reservation -> {
                    model.addAttribute("reservation", reservation);
                    return "reservation/reservation";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/cancel")
    public String cancelReservation(@PathVariable("id") Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        }

        if (reservationService.cancelReservation(id)) {
            return "redirect:/welcome";
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Don't have access to cancel reservation");
        }
    }

}
