package com.example.demoone.controller;

import com.example.demoone.entity.Post;
import com.example.demoone.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post){
        postService.addPost(post);
        var location = UriComponentsBuilder.fromPath("/posts/" + post.getId()).build().toUri();
        return ResponseEntity.created(location).body(post);
    }

}
