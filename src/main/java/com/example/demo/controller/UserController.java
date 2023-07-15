package com.example.demo.controller;


import com.example.demo.Util.UserValid;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private UsersService usersService;
    private UserValid userValid;

    @Autowired
    public UserController(UsersService usersService, UserValid userValid) {
        this.usersService = usersService;
        this.userValid = userValid;
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/update";
        usersService.update(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@ModelAttribute("user") User user) {
        usersService.delete(user);
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String editSolo(Principal principal, ModelMap model) {
        User user = usersService.findByUsername(principal.getName());
        List<Role> role = user.getRoles();
        model.addAttribute("message", user);
        model.addAttribute("roles", role);
        return "/editSolo";
    }


}

