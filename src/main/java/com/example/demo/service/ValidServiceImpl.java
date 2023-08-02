package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValidServiceImpl implements ValidService {

    private UserRepository userRepository;

    @Autowired
    public ValidServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public boolean findByname(User user) {
        return (userRepository.findByUsername(user.getUsername())) == null;
    }
}
