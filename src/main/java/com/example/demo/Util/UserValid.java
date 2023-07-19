package com.example.demo.Util;

import com.example.demo.model.User;
import com.example.demo.service.ValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValid implements Validator {


    private ValidService validService;

    @Autowired
    public UserValid(ValidService validService) {
        this.validService = validService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (!validService.findByname(user)) {
            errors.rejectValue("username", "", "пользватель с таким именем существует");
        }
    }
}
