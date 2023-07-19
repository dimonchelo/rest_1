package com.example.demo.controller;

import com.example.demo.Util.UserValid;
import com.example.demo.model.Role;
import com.example.demo.model.User;
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
import java.util.Set;


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
        Set<Role> role = user.getRoles();
        model.addAttribute("role", role);
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
    public String createUser(@ModelAttribute("User") @Valid User user, BindingResult bindingResult,
                             @RequestParam(value = "checkedRoles") String[] selectResult) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/admin/new";
        for (String s : selectResult) {
            user.addRole(roleService.getRole("ROLE_" + s));
        }
        usersService.add(user);
        return "redirect:/admin";
    }


    @PutMapping("/{id}/edit")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, ModelMap model,
                         @RequestParam(value = "checkedRoles") String[] selectResult) {
        userValid.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/admin/users";
        for (String s : selectResult) {
            user.addRole(roleService.getRole("ROLE_" + s));
        }
        model.addAttribute("roles", roleService.listRole());
        usersService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@ModelAttribute("user") User user) {
        usersService.delete(user);
        return "redirect:/admin";
    }

}
