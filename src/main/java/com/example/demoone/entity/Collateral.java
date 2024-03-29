package com.example.demoone.entity;

import com.example.demoone.dto.RegistrationDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "collaterals")
@SequenceGenerator(name = "collateralIdGenerator", sequenceName = "collaterals_id_seq", allocationSize = 1)
public class Collateral {
    public Collateral(RegistrationDto.Collateral dto){
        this.type = dto.getType();
        this.value = dto.getValue();
    }

    public enum CollateralType{
        CAR,
        HOUSE,
        LAND
    }
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collateralIdGenerator")
    @Id
    private Integer id;

    @Column(name = "coll_type", nullable = false)
    private CollateralType type;

    @Column(name = "coll_value", nullable = false)
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
}
