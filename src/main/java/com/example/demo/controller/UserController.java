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
@RequestMapping("/user")
public class UserController {
    private UsersService usersService;
    private UserValid userValid;

    @Autowired
    public UserController(UsersService usersService, UserValid userValid) {
        this.usersService = usersService;
        this.userValid = userValid;
    }


    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/user/update";
        usersService.update(user);
        return "redirect:/user/login";
    }

    @DeleteMapping("/{id}")
    public String deleteSUser(@ModelAttribute("user") User user) {
        usersService.delete(user);
        return "redirect:/user/login";
    }
    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute(usersService.show(id));
        return "/user/update";
    }

    @GetMapping()
    public String editSolo(Principal principal, ModelMap model) {
        User user = usersService.findByUsername(principal.getName());
//        List<Role> role = user.getRoles();
        model.addAttribute("message", user);
//        model.addAttribute("roles", role);
        return "/user/editSolo";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "/user/login";
    }


}

