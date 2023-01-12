package com.example.demoone.dto;

import com.example.demoone.entity.Collateral.CollateralType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class RegistrationDto {
  private Loan loan;
  private Customer customer;
  private List<Collateral> collaterals;

  @Setter
  @Getter
  public static class Loan{
      private String loanNumber;
      private Double amount;
      private Integer term;
      private Double interestRate;
  }

  @Setter
  @Getter
  public static class Customer{
    private String privateNumber;
    private String firstName;
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
  }

  @Setter
  @Getter
  public static class Collateral{
    private CollateralType type;
    private Double value;
  }

}

