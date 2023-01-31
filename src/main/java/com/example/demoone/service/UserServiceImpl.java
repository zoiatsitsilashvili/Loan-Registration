package com.example.demoone.service;

import com.example.demoone.entity.User;
import com.example.demoone.exception.NotFoundException;
import com.example.demoone.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User addUser(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        var foundUser = getUserById(id);
        foundUser.setActive(false);
        userRepository.save(foundUser);
    }

    @Override
    public User update(int id, User user) {
        var foundUser = getUserById(id);
        foundUser.setUsername(user.getUsername());
        foundUser.setPassword(user.getPassword());
        foundUser.setEmail(user.getEmail());
        userRepository.save(foundUser);
        return foundUser;
    }

}
