package com.example.demoone.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor

@Table(name = "posts")
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;


    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    private boolean delete;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

    @PrePersist
    public void prePersist(){
        createDate = LocalDateTime.now();
    }
}
