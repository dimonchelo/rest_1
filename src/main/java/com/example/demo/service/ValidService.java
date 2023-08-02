package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface ValidService {

    @Transactional
    boolean findByname(User user);
}
