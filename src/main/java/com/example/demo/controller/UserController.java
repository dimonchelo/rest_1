package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {
    private UsersService usersService;


    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping("/rest")
    public String userPage(Principal principal, ModelMap model) {
        User user = usersService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/rest/userRest";
    }



}

