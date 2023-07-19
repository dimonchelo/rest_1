package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public List<Role> listRole() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role getRole(String userRole) {
        return roleRepository.findByUserRole(userRole);
    }

    @Transactional
    public Role getRoleById(Long id) {
        return roleRepository.getById(id);
    }


    @Transactional
    public void addRole(Role role) {
        roleRepository.save(role);
    }
}
