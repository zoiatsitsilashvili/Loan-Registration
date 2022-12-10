package com.example.demoone.service;

import com.example.demoone.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    List <User> getUsers();
    User getUserById(int id);
    User getUserByUsername (String username);
}
