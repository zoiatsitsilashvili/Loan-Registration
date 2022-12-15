package com.example.demoone.service;

import com.example.demoone.entity.Post;
import com.example.demoone.exception.NotFoundException;
import com.example.demoone.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostServiceImpl(PostRepository postRepository,
                           UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPost(int id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
    }

    @Override
    public List<Post> getPostsByUserId(int userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public Post addPost(Post post) {
        post.setId(null);
        if(post.getUser().getId() == null) {
            userService.addUser(post.getUser());
        }
        return postRepository.save(post);
    }


}


