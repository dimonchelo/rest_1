package com.example.demo.controller;

import com.example.demo.Util.UserValid;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping()
    public String allUsers(Principal principal, ModelMap model) {
        User user = usersService.findByUsername(principal.getName());
        model.addAttribute("users", usersService.listUser());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.listRole());
        return "/admin/users";
    }

    @GetMapping("/new")
    public String newUser(Principal principal, Model model) {
        User user = usersService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.listRole());
        return "/admin/new";
    }

    @PostMapping("/new_procces")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//        userValid.validate(user, bindingResult);
//        if (bindingResult.hasErrors())
//            return "/admin/new";
        getUserRoles(user);
        usersService.add(user);
        return "redirect:/admin";
    }

    //    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable("id") Long id, ModelMap modelMap) {
//        modelMap.addAttribute(usersService.show(id));
//        return "/admin/update";
//    }


    @PutMapping("/{id}/edit")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, ModelMap model) {
//        userValid.validate(user, bindingResult);
//        if (bindingResult.hasErrors())
//            return "/admin/users";
        model.addAttribute("roles", roleService.listRole());
        getUserRoles(user);
        usersService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@ModelAttribute("user") User user) {
        usersService.delete(user);
        return "redirect:/admin";
    }

    private void getUserRoles(User user) {
        user.setRoles(user.getRoles().stream()
                .map(role -> roleService.getRole(role.getName()))
                .collect(Collectors.toSet()));
    }
}
