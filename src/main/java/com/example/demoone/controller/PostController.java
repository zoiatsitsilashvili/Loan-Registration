package com.example.demoone.controller;

import com.example.demoone.entity.Post;
import com.example.demoone.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping
    public List<Post> getPosts(){
        return postService.getPosts();
    }
    @GetMapping("/{id}")
    public Post getPost(@PathVariable int id){
        return postService.getPost(id);
    }
}
