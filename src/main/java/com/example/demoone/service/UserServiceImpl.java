package com.example.demoone.service;

import com.example.demoone.dtu.UserSearchParams;
import com.example.demoone.entity.User;
import com.example.demoone.entity.User_;
import com.example.demoone.exception.NotFoundException;
import com.example.demoone.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page <User> getUsers(UserSearchParams params, Pageable pageable) {
        return userRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.conjunction();
           if (params.getId() != null) {
               predicate = cb.and(predicate, cb.equal(root.get(User_.ID), params.getId()));
           }
           // აქ რადგან დაჯოინებული გვაქვს და პოსტსერვისში ძებნის პარამეტრშიც გვაქვს გათვალისწინებული
            // მერე გამახსენდა
            // if(StringUtils.isNotEmpty(params.getUsername())){
             //  predicate = cb.and(predicate, cb.like(root.get(User_.USERNAME), '%' +params.getUsername() + '%'));
           //}

           if(StringUtils.isNotEmpty(params.getEmail())){
               predicate = cb.and(predicate, cb.like(root.get(User_.EMAIL), '%' + params.getEmail() + '%'));
           }
           if(params.getCreateDate() != null){
               predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get(User_.CREATE_DATE), params.getCreateDate()));
           }

           return predicate;
        }, pageable);
    }
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new NotFoundException("User not found"));
    }
    @Override
    public User addUser (User user){
        user.setId(null);
        return userRepository.save(user);
    }
    @Override
    public void delete(int id){
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
