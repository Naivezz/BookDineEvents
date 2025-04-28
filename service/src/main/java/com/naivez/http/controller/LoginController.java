package com.naivez.http.controller;

import com.naivez.entity.User;
import com.naivez.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @GetMapping("/welcome")
    public String welcomePage(Model model, Principal principal) {
        if (principal != null) {
            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            model.addAttribute("user", user);
        }
        return "welcome";
    }
}
