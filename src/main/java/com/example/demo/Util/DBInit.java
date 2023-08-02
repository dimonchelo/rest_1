package com.example.demo.Util;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleServiceImpl;
import com.example.demo.service.UsersService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit {
    private final UsersService userService;
    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public DBInit(UsersService userService, RoleServiceImpl roleServiceImpl) {
        this.userService = userService;
        this.roleServiceImpl = roleServiceImpl;
    }

    @PostConstruct
    private void dataBaseInit() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        Set<Role> adminSet = new HashSet<>();
        Set<Role> userSet = new HashSet<>();

        roleServiceImpl.addRole(roleAdmin);
        roleServiceImpl.addRole(roleUser);

        adminSet.add(roleAdmin);
        adminSet.add(roleUser);
        userSet.add(roleUser);

        User admin = new User("dima", "111", adminSet, "lastdima");


        userService.add(admin);
    }
}