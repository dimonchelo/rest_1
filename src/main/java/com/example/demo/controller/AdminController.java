package com.example.demo.controller;

import com.example.demo.Util.UserValid;
import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class AdminController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserValid userValid;
    @GetMapping("/new")
    public String newUser(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "/new";
    }

    @PostMapping("/new_procces")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/new";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersService.add(user);
        return "redirect:/admin";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute(usersService.show(id));
        return "/update";
    }
    @GetMapping("/admin")
    public String allUsers(ModelMap model) {
        model.addAttribute("message", usersService.listUser());
        return "/users";
    }
}
