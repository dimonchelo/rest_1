package com.example.demo.controller;



import com.example.demo.Util.UserValid;
import com.example.demo.model.User;
import com.example.demo.service.RoleServiceImpl;
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

    private RoleServiceImpl roleServiceImpl;

    @Autowired
    public AdminController(UsersService usersService, UserValid userValid, RoleServiceImpl roleServiceImpl) {
        this.usersService = usersService;
        this.userValid = userValid;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping("/rest")
    public String userPage(Principal principal, ModelMap model) {
        User user = usersService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/rest/adminRest";
    }

}
