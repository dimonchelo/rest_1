package com.example.demo.service;

import com.example.demo.model.Role;
import org.springframework.transaction.annotation.Transactional;

public interface RoleService {
    @Transactional
    void addRole(Role role);
}
