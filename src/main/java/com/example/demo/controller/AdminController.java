package com.example.demo.controller;

import com.example.demo.Util.UserValid;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public AdminController(UsersService usersService, UserValid userValid) {
        this.usersService = usersService;
        this.userValid = userValid;
    }


    @GetMapping("/new")
    public String newUser(Principal principal, ModelMap model) {
        model.addAttribute("user", new User());
        User user = usersService.findByUsername(principal.getName());
        List<Role> role = user.getRoles();
        model.addAttribute("messagesolo", user);
        model.addAttribute("role", role);
        model.addAttribute("message", usersService.listUser());
        model.addAttribute("roles", usersService.listRole());
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

    //    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable("id") Long id, ModelMap modelMap) {
//        modelMap.addAttribute(usersService.show(id));
//        return "/admin/update";
//    }
    @GetMapping()
    public String allUsers(Principal principal, ModelMap model) {
        User user = usersService.findByUsername(principal.getName());
        List<Role> role = user.getRoles();
        model.addAttribute("messagesolo", user);
        model.addAttribute("role", role);
        model.addAttribute("message", usersService.listUser());
        model.addAttribute("roles", usersService.listRole());
        return "/admin/users";
    }

    @PutMapping("/{id}/edit")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, ModelMap model) {
//        userValid.validate(user, bindingResult);
//        if (bindingResult.hasErrors())
//            return "/admin/users";
        model.addAttribute("roles", usersService.listRole());
//        getUserRoles(user);
        usersService.update(user, user.getId());
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@ModelAttribute("user") User user) {
        usersService.delete(user);
        return "redirect:/admin";
    }

//    private void getUserRoles(User user) {
//        List<Role> list = new ArrayList<>();
//        for (Role role : user.getRoles()) {
//            Role byName = roleRepository.findByName(role.getName());
//            list.add(byName);
//        }
//        user.setRoles(list);
//
//    }
}
