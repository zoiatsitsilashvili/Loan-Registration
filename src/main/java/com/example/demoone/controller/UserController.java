package com.example.demoone.controller;

import com.example.demoone.dto.UserSearchParams;
import com.example.demoone.entity.Post;
import com.example.demoone.entity.User;
import com.example.demoone.service.PostService;
import com.example.demoone.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public Page<User> getUsers(@RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false, defaultValue = "10") int size,
                               @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction,
                               @RequestParam(required = false, defaultValue = "id")String field,
                               UserSearchParams params) {
        Sort sorter = Sort.by(direction, field);
        return userService.getUsers(params, PageRequest.of(page,size, sorter));
    }
    @GetMapping("/{id}")
    public User getUser (@PathVariable int id){
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
