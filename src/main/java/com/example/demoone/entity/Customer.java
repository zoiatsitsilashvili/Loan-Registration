package com.example.demoone.entity;

import com.example.demoone.dto.RegistrationDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "customers")
@SequenceGenerator(name = "customerIdGenerator", sequenceName = "customers_id_seq", allocationSize = 1)
public class Customer {
    public Customer(RegistrationDto.Customer dto){
        this.privateNumber = dto.getPrivateNumber();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.birthDate = dto.getBirthDate();
    }


    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerIdGenerator")
    @Id
    private Integer id;
    @Column(name = "private_number", nullable = false, unique = true)
    private String privateNumber;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Customer(String firstName, String lastName, String privateNumber, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.privateNumber = privateNumber;
        this.birthDate = LocalDate.parse(birthDate);
    }


    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @JsonIgnore
    @OneToMany (mappedBy = "customer")
    private List<Loan> loans;


}
