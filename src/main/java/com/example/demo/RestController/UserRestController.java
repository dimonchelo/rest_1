package com.example.demo.RestController;


import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import java.util.Optional;

@RestController
@RequestMapping("/user/api/user")
public class UserRestController {

    private final UsersService userService;

    public UserRestController(UsersService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<User> getUser(Principal principal) {
        return Optional.ofNullable(userService.findByUsername(principal.getName()))
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


}
