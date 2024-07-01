package com.dev.timetracker.controller;

import com.dev.timetracker.entity.User;
import com.dev.timetracker.service.UserService;
import jakarta.validation.Valid;
import com.dev.timetracker.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Handler method to handle home page request
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // Handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Create model object to store form data
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    // Handler to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        try {
            User existingUser = userService.findUserByEmail(userDto.getEmail());

            if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
                result.rejectValue("email", null, "There is already an account registered with the same email.");
                model.addAttribute("errorMessage", "Registration failed: User already exists.");
                model.addAttribute("error", true); // Setting error attribute to true
                return "register";
            }

            if (result.hasErrors()) {
                model.addAttribute("user", userDto);
                return "register";
            }

            userService.saveUser(userDto);
            return "redirect:/register?success";
        } catch (Exception e) {
            logger.warn("An error occurred during registration due to: " + e.getMessage(), e);
            model.addAttribute("errorMessage", "An error occurred during registration due to: " + e.getMessage());
            model.addAttribute("user", userDto); // This is to ensure that the form is repopulated with the user's input in case of an error.
            return "register";
        }
    }
}
