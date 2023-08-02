package com.example.demo.Util;

import com.example.demo.model.User;
import com.example.demo.service.ValidServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValid implements Validator {


    private ValidServiceImpl validServiceImpl;

    @Autowired
    public UserValid(ValidServiceImpl validServiceImpl) {
        this.validServiceImpl = validServiceImpl;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (!validServiceImpl.findByname(user)) {
            errors.rejectValue("username", "", "пользватель с таким именем существует");
        }
    }
}
