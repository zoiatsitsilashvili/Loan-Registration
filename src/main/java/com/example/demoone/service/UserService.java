package com.example.demoone.service;

import com.example.demoone.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getUserById(int id);
    User getUserByUserName (String username);
    User addUser(User user);
    void delete(int id);
    User update(int id, User user);

    List<User> getUsers();

}
