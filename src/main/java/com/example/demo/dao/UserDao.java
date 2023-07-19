package com.example.demo.dao;
import com.example.demo.model.User;
import java.util.List;

public interface UserDao {
    void add(User user);

    void delete(User user);

    void update(User user);

    List<User> listUsers();

    User show(int id);

}
