package com.example.demoone.entity;

import com.example.demoone.dto.RegistrationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "loans")
@SequenceGenerator(name = "loanIdGenerator", sequenceName = "loan_id_seq", allocationSize = 1)
public class Loan {
    public Loan(RegistrationDto.Loan dto){
        if(dto == null){
            throw new IllegalArgumentException("loan is null");
        }
        this.amount = dto.getAmount();
        this.interestRate = dto.getInterestRate();
        this.loanNumber = dto.getLoanNumber();
        this.term = dto.getTerm();
    }

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loanIdGenerator")
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "loan_number", nullable = false, length = -1)
    private String loanNumber;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;

    @Column(name = "term ", nullable = false)
    private int term;

    @Column(name = "interest ", nullable = false)
    private Double interest;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn (name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "loan")
    private List<Collateral> collaterals;
}
