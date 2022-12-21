package com.example.demoone.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity

@Table(name = "posts")
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = -1)
    private String title;

    @Column(name = "body", nullable = false, length = -1)
    private String body;


    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    private boolean delete;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @PrePersist
    public void prePersist(){
        createDate = LocalDateTime.now();
    }
}
