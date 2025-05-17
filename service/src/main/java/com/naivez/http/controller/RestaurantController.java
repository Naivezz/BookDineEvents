package com.naivez.http.controller;

import com.naivez.dto.PageResponse;
import com.naivez.dto.restaurant.RestaurantCreateEditDto;
import com.naivez.dto.restaurant.RestaurantFilter;
import com.naivez.dto.restaurant.RestaurantReadDto;
import com.naivez.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public String findAll(Model model, RestaurantFilter filter, Pageable pageable) {
        Page<RestaurantReadDto> page = restaurantService.findAll(filter, pageable);
        model.addAttribute("restaurants", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "restaurant/restaurants";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        return restaurantService.findById(id)
                .map(restaurant -> {
                    model.addAttribute("restaurant", restaurant);
                    return "restaurant/restaurant";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createForm(Model model, @ModelAttribute("restaurant") RestaurantCreateEditDto restaurant) {
        return "restaurant/create";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(@ModelAttribute @Valid RestaurantCreateEditDto restaurant) {
        RestaurantReadDto created = restaurantService.create(restaurant);
        return "redirect:/restaurants/" + created.getId();
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String edit(@PathVariable Long id, Model model) {
        return restaurantService.findById(id)
                .map(restaurant -> {
                    model.addAttribute("restaurant", restaurant);
                    return "restaurant/edit";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@PathVariable Long id, @ModelAttribute @Valid RestaurantCreateEditDto restaurant) {
        return restaurantService.update(id, restaurant)
                .map(updated -> "redirect:/restaurants/" + id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id) {
        if (!restaurantService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/restaurants";
    }
}