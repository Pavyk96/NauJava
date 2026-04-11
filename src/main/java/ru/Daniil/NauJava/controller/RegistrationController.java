package ru.Daniil.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        try {
            userService.createUser(user.getFullName(), user.getEmail(), user.getPhone(), user.getPassword());
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка регистрации: " + e.getMessage());
            return "registration";
        }
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}
