package com.example.demoone.service;

import com.example.demoone.dtu.UserSearchParams;
import com.example.demoone.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public interface UserService {
    Page<User> getUsers(UserSearchParams params, Pageable pageable);
    User getUserById(int id);
    User getUserByUserName (String username);
    User addUser(User user);
    void delete(int id);
    User update(int id, User user);
}
