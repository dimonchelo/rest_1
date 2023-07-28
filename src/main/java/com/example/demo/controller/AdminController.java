package com.example.demo.controller;

import com.example.demo.Util.UserValid;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private UsersService usersService;
    private UserValid userValid;

    private RoleService roleService;

    @Autowired
    public AdminController(UsersService usersService, UserValid userValid, RoleService roleService) {
        this.usersService = usersService;
        this.userValid = userValid;
        this.roleService = roleService;
    }

    @GetMapping("/rest")
    public String userPage(Principal principal, ModelMap model) {
        User user = usersService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/rest/adminRest";
    }

}
