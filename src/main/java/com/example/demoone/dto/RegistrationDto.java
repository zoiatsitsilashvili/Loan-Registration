package com.example.demoone.dto;

import com.example.demoone.entity.Collateral.CollateralType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
  @Valid
  @NotNull
  private Loan loan;
  @Valid
  @NotNull
  private Customer customer;
  @Valid
  @NotNull
  private List<Collateral> collaterals;

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Loan{
    @NotNull
      private String loanNumber;
    @NotNull
    @Min(100)
      private Double amount;
    @Min(2)
    @Max(100)
      private Integer term;
    @NotNull
    @Min(1)
      private Double interestRate;
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Customer{
    @NotNull
    private String privateNumber;
    private String firstName;
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Collateral{
    @NotNull
    private CollateralType type;
    @NotNull
    private Double value;
  }

}

