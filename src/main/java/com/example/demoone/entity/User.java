package com.example.demoone.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username", nullable = false, length = -1)
    private String username;

    @Column(name = "password", nullable = false, length = -1)
    private String password;

    @Column(name = "email", nullable = false, length = -1)
    private String email;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @Column(name = "active", nullable = false)
    private boolean active;


}