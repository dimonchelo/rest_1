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
public class UserController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserValid userValid;
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/update";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersService.update(user, id);
        return "redirect:/user";
    }
    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("user") User user) {
        usersService.delete(user);
        return "redirect:/login";
    }
    @GetMapping("/user")
    public String editSolo(Principal principal , ModelMap model) {
        User user = usersService.findByUsername(principal.getName());
        model.addAttribute("message", user );
        return "/editSolo";
    }


}

