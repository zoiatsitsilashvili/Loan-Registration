package com.example.demoone.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@Entity
@Table(name = "posts")
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "title", nullable = false, length = -1)
    private String title;

    @Column(name = "body", nullable = false, length = -1)
    private String body;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "create_data", nullable = false)
    private Timestamp createData;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false,insertable = false)
    private User user;

}
