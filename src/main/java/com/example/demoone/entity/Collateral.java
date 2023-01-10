package com.example.demoone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "collateral", schema = "public", catalog = "postgres")
@SequenceGenerator(name = "collateralIdGenerator", sequenceName = "collaterals_id_seq", allocationSize = 1)
public class Collateral {
    public enum CollateralType{
        CAR, HOUSE, LAND
    }
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collateralIdGenerator")
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false)
    private CollateralType type;

    @Column(name = "value", nullable = false)
    private Double value;

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
    @JoinColumn(name = "loan_id")
    private Loan loan;
}
