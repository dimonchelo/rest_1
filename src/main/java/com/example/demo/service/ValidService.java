package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ValidService {
    @Autowired
    private UserRepository userRepository;
    public Optional<User> findByname (String username) {
        return Optional.ofNullable((userRepository.findByUsername(username)));
    }
}
