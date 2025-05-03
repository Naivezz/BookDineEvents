package com.naivez.http.controller;

import com.naivez.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservation/reservations";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model, Authentication authentication) {
        if (!reservationService.hasAccessToReservation(id, authentication)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return reservationService.findById(id)
                .map(reservation -> {
                    model.addAttribute("reservation", reservation);
                    return "reservation/reservation";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/cancel")
    public String cancelReservation(@PathVariable("id") Long id) {
        if (reservationService.cancelReservation(id)) {
            return "redirect:/welcome";
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
