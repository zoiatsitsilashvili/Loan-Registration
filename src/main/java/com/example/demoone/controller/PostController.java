package com.example.demoone.controller;

import com.example.demoone.dtu.PostSearchParams;
import com.example.demoone.entity.Post;
import com.example.demoone.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }
    @GetMapping
    public Page<Post> getPosts(@RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false, defaultValue = "10") int size,
                               @RequestParam(required = false, defaultValue = "DESC")Sort.Direction direction,
                               @RequestParam(required = false, defaultValue = "id")String field,
                               PostSearchParams params)    {
        Sort sorter = Sort.by(direction, field);
        return postService.getPosts(params, PageRequest.of(page,size, sorter));
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
