package com.naivez.http.controller;

import com.naivez.dto.user.UserCreateEditDto;
import com.naivez.dto.user.UserFilter;
import com.naivez.entity.enums.Role;
import com.naivez.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String findAll(Model model, UserFilter filter) {
        model.addAttribute("users", userService.findAll(filter));
        return "user/users";
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userCheckAccess.isSelfOrAdmin(#id, authentication)")
    public String findById(@PathVariable("id") Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user/registration";
    }

    @PostMapping("/registration")
    public String create(@ModelAttribute @Valid UserCreateEditDto user) {
        System.out.println(user);
        userService.create(user);
        return "redirect:/login";
    }

    @GetMapping("/{id}/update")
    @PreAuthorize("@userCheckAccess.isSelfOrAdmin(#id, authentication)")
    public String edit(@PathVariable("id") Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    return "user/edit";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("@userCheckAccess.isSelfOrAdmin(#id, authentication)")
    public String update(@PathVariable Long id, @ModelAttribute @Valid UserCreateEditDto user) {
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("@userCheckAccess.isSelfOrAdmin(#id, authentication)")
    public String delete(@PathVariable("id") Long id, Authentication authentication) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.getAuthority()));

        return isAdmin ? "redirect:/users" : "redirect:/login";
    }
}
