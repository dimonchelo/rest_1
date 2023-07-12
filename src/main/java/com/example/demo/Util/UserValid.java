package com.example.demo.Util;

import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import com.example.demo.service.ValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;

@Component
public class UserValid implements Validator {

    @Autowired
    private ValidService validService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (!(validService.findByname(user.getUsername())).equals(user)) {

        } else {
            errors.rejectValue("username", "", "пользватель с таким именем существует");
        }
    }
}
