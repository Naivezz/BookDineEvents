package com.naivez.http.controller;

import com.naivez.dto.event.EventCreateEditDto;
import com.naivez.dto.event.EventReadDto;
import com.naivez.dto.restaurant.RestaurantReadDto;
import com.naivez.service.EventService;
import com.naivez.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final RestaurantService restaurantService;

    @GetMapping
    public String findAll(
            @RequestParam(required = false) Long restaurantId,
            Model model) {

        List<EventReadDto> events;
        if (restaurantId != null) {
            events = eventService.findByRestaurantId(restaurantId);
            model.addAttribute("restaurant", restaurantService.findById(restaurantId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        } else {
            events = eventService.findAll();
        }

        model.addAttribute("events", events);
        return "event/events";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return eventService.findById(id)
                .map(event -> {
                    model.addAttribute("event", event);
                    return "event/event";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createForm(Model model, @ModelAttribute("event") EventCreateEditDto event) {
        model.addAttribute("restaurants", restaurantService.findAll());
        return "event/create";
    }

    @PostMapping
    public String createEvent(@Valid EventCreateEditDto dto, RedirectAttributes attrs) {
        eventService.create(dto);
        attrs.addFlashAttribute("success", "Event was successfully added");
        return "redirect:/events";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        EventReadDto event = eventService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<RestaurantReadDto> restaurants = restaurantService.findAll();

        model.addAttribute("event", event);
        model.addAttribute("restaurants", restaurants);
        return "event/edit";
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@PathVariable Long id, @ModelAttribute @Valid EventCreateEditDto event) {
        return eventService.update(id, event)
                .map(it -> "redirect:/events/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        if (!eventService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/events";
    }
}
