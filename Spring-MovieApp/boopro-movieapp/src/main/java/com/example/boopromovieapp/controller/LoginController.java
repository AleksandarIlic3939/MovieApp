package com.example.boopromovieapp.controller;

import com.example.boopromovieapp.controller.dtos.UserDTO;
import com.example.boopromovieapp.models.User;
import com.example.boopromovieapp.security.LoginCredentials;
import com.example.boopromovieapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/dashboard/login")
    public String loginForm(Model model) {
        model.addAttribute("userCredentials", new LoginCredentials());
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDTO());
        model.addAttribute("title", "Register");
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@ModelAttribute("userDto") UserDTO userDTO,
                              BindingResult result,
                              Model model) {
        try {

            if (result.hasErrors()) {
                model.addAttribute("userDto", userDTO);
                return "register";
            }
            String email = userDTO.getEmail();
            User user = userService.getUserByEmail(email);
            if (user != null) {
                model.addAttribute("userDto", userDTO);
                model.addAttribute("emailError", "Email is not unique!");
                return "register";
            }
            if (userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
                userService.save(userDTO);
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("userDto", userDTO);
            } else {
                model.addAttribute("userDto", userDTO);
                model.addAttribute("passwordError", "Wrong password, try again!");
                return "register";
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            model.addAttribute("errors", "Server's error, please try again later!");
        }

        return "register";
    }

    @RequestMapping("/admin/index")
    private String home(Model model) {
        model.addAttribute("title", "Home Page");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            return "redirect:/login";
//        }
        return "index";
    }

}