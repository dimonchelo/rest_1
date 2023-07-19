package com.example.demo.controller;

import com.example.demo.Util.UserValid;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private UsersService usersService;
    private UserValid userValid;
    private RoleService roleService;

    @Autowired
    public UserController(UsersService usersService, UserValid userValid, RoleService roleService) {
        this.usersService = usersService;
        this.userValid = userValid;
        this.roleService = roleService;
    }


    @PutMapping("/{id}/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam(value = "checkedRoles") String[] selectResult) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/user/update";
        for (String s : selectResult) {
            user.addRole(roleService.getRole("ROLE_" + s));
        }
        usersService.update(user);
        return "redirect:/user/login";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteSUser(@ModelAttribute("user") User user) {
        usersService.delete(user);
        return "redirect:/user/login";
    }


    @GetMapping()
    public String editSolo(Principal principal, ModelMap model) {
        User user = usersService.findByUsername(principal.getName());
        Set<Role> role = user.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", role);
        return "/user/editSolo";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/user/login";
    }


}

