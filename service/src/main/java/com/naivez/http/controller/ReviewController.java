package com.naivez.http.controller;

import com.naivez.dto.PageResponse;
import com.naivez.dto.review.ReviewCreateEditDto;
import com.naivez.dto.review.ReviewFilter;
import com.naivez.dto.review.ReviewReadDto;
import com.naivez.service.ReviewService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    @GetMapping
    public String findAll(Model model, ReviewFilter filter, Pageable pageable) {
        Page<ReviewReadDto> page = reviewService.findAll(filter, pageable);
        model.addAttribute("reviews", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("restaurantId", filter.getRestaurantId());
        return "review/reviews";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, Principal principal) {
        return reviewService.findAll().stream()
                .filter(review -> review.getId().equals(id))
                .findFirst()
                .map(review -> {
                    model.addAttribute("review", review);
                    if (principal != null) {
                        model.addAttribute("currentUsername", principal.getName());
                    }
                    return "review/review";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public String createForm(@RequestParam Long restaurantId, Model model) {
        ReviewCreateEditDto review = ReviewCreateEditDto.builder()
                .restaurantId(restaurantId)
                .build();
        model.addAttribute("review", review);
        return "review/create";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid ReviewCreateEditDto review, Principal principal) {
        String email = principal.getName();
        ReviewReadDto created = reviewService.create(review, email);
        return "review/success";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        if (!reviewService.delete(id, email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return "redirect:/reviews";
    }

}

