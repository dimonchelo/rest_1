package com.example.demo.controller;

import com.example.demo.Util.UserValid;
import com.example.demo.model.Role;
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

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UsersService usersService;
    private UserValid userValid;
    @Autowired
    public AdminController(UsersService usersService, UserValid userValid) {
        this.usersService = usersService;
        this.userValid = userValid;
    }


    @GetMapping("/new")
    public String newUser(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "/admin/new";
    }

    @PostMapping("/new_procces")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/admin/new";
        usersService.add(user);
        return "redirect:/admin";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute(usersService.show(id));
        return "/admin/update";
    }
    @GetMapping()
    public String allUsers(Principal principal, ModelMap model) {
        User user = usersService.findByUsername(principal.getName());
        List<Role> role = user.getRoles();
        model.addAttribute("messagesolo", user);
        model.addAttribute("roles", role);
        model.addAttribute("message", usersService.listUser());
        return "/admin/users";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/admin/update";
        usersService.update(user, id);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("user") User user) {
        usersService.delete(user);
        return "redirect:/admin";
    }

}
