package com.example.demoone.dto;

import com.example.demoone.entity.Collateral.CollateralType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Setter
@Getter
public class LoanSearchParams {
    private String privateNumber;
    private String firstName;
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    private Integer id;
    private String loanNumber;
    private Double amount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate updateAt;
    private CollateralType type;
}
