package com.example.demoone.service;

import com.example.demoone.dtu.PostSearchParams;
import com.example.demoone.entity.Post;
import com.example.demoone.entity.Post_;
import com.example.demoone.entity.User;
import com.example.demoone.entity.User_;
import com.example.demoone.exception.NotFoundException;
import com.example.demoone.repository.PostRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
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
    public Page<Post> getPosts(PostSearchParams params, Pageable pageable) {
        return postRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if(params.getId()!= null) {
                predicate = cb.and(predicate, cb.equal(root.get(Post_.ID), params.getId()));
            }
            // if(params.getTitle() != null && !params.getTitle().isEmpty())
            if(StringUtils.isNotEmpty(params.getTitle())) {
                predicate = cb.and(predicate, cb.like(root.get(Post_.TITLE), '%' + params.getTitle() + '%'));
            }
            if(StringUtils.isNotEmpty(params.getBody())) {
                predicate = cb.and(predicate, cb.like(root.get(Post_.BODY), '%' + params.getBody() + '%'));
            }
            if(params.getCreateDateFrom() != null){
               var createDateFrom = params.getCreateDateFrom().atStartOfDay();
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get(Post_.CREATE_DATE), createDateFrom));
            }
            if(params.getCreateDateTo() != null){
                var creatDateTo = params.getCreateDateTo().atTime(23, 59, 59);
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get(Post_.CREATE_DATE), creatDateTo));
            }
            if(StringUtils.isNotEmpty(params.getUsername())){
                Join<Post, User> user = root.join(Post_.USER, JoinType.LEFT);
                predicate = cb.and(predicate, cb.like(user.get(User_.USERNAME), params.getUsername() + '%'));
            }
            return predicate;
        }, pageable);
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
    @Override
    public void delete(int id){
       var foundUser = getPost(id);
       foundUser.setDelete(true);
       postRepository.save(foundUser);

   }
}


