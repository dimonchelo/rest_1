package com.example.demo.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/api/admin")
public class AdminRestController {
    private final UsersService userService;

    public AdminRestController(UsersService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return Optional.ofNullable(userService.listUser())
                .map(userList -> new ResponseEntity<>(userList, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//        List<User> userList = userService.listUser();
//        if (userList == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.show(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            userService.add(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping( "/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            userService.update(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }
}
