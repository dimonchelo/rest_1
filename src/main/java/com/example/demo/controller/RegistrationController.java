package com.example.demo.controller;

import com.example.demo.Util.UserValid;
import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class RegistrationController {
    private UsersService usersService;
    private UserValid userValid;


    @Autowired
    public RegistrationController(UsersService usersService, UserValid userValid) {
        this.usersService = usersService;
        this.userValid = userValid;
    }

    public RegistrationController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "/registration";
    }

    @PostMapping("/registration_procces")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/registration";
        usersService.add(user);
        return "redirect:/login";
    }

    @GetMapping("/createAdmin")
    public String createAdmin(ModelMap model) {
        model.addAttribute("user", new User());
        return "/createAdmin";
    }

    @PostMapping("/createAdmin_procces")
    public String addAdmin(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/createAdmin";
        usersService.addAdmin(user);
        return "redirect:/login";
    }
}
