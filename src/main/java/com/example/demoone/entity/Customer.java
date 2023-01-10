package com.example.demoone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "customer", schema = "public", catalog = "postgres")
@SequenceGenerator(name = "customerIdGenerator", sequenceName = "customers_id_seq", allocationSize = 1)
public class Customer {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerIdGenerator")
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "private_number", nullable = false, unique = true)
    private String privateNumber;
    @Column(name = "first_name", nullable = false, length = -1)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private int lastName;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;



    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @JsonIgnore
    @OneToMany (mappedBy = "customer")
    private List<Loan> loans;


}
