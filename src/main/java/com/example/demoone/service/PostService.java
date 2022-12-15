package com.example.demoone.service;

import com.example.demoone.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PostService {
    List<Post> getPosts();
    Post getPost(int id);

    List<Post> getPostsByUserId(int userId);
    Post addPost(Post post);

}
