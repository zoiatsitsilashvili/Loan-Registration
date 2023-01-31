package com.example.demoone.controller;

import com.example.demoone.entity.Post;
import com.example.demoone.entity.User;
import com.example.demoone.service.PostService;
import com.example.demoone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final PasswordEncoder passwordEncoder;



    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }
    @GetMapping("/{id}")
    public User getUser (@PathVariable int id){
        System.out.println(passwordEncoder.encode("zoia"));
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/posts")
    public List<Post> getUserPosts (@PathVariable int id){
        return postService.getPostsByUserId(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable int id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        userService.addUser(user);
        var location = UriComponentsBuilder.fromPath("/users/" +user.getId()).build().toUri();
        return ResponseEntity.created(location).body(user);
    }
    @PutMapping ("/{id}")
    public User update (@RequestBody User user, @PathVariable int id){
        return userService.update(id, user);
    }

}
