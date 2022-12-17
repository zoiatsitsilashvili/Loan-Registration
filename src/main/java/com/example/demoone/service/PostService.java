package com.example.demoone.service;

import com.example.demoone.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PostService {
    Page<Post> getPosts(Pageable pageable);
    Post getPost(int id);

    List<Post> getPostsByUserId(int userId);
    Post addPost(Post post);

}
