package com.example.demoone.controller;

import com.example.demoone.entity.Post;
import com.example.demoone.entity.User;
import com.example.demoone.service.PostService;
import com.example.demoone.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }
    @GetMapping
    public List<User> getUsers(){

        return userService.getUsers();
    }
    @GetMapping("/{id}")
    public User getUser (@PathVariable int id){

        return userService.getUserById(id);
    }

    @GetMapping("/{id}/posts")
    public List<Post> getUserPosts (@PathVariable int id){
        return postService.getPostsByUserId(id);
    }
}
