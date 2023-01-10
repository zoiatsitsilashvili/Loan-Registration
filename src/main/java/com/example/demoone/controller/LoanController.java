package com.example.demoone.controller;

import com.example.demoone.dto.RegistrationDto;
import com.example.demoone.entity.Loan;
import com.example.demoone.service.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    @GetMapping
    public Page<Loan> getLoans (RegistrationDto registrationDto){
        return loanService.getLoans();
    }

    @PostMapping
    public ResponseEntity<Loan> addLoan(@RequestBody RegistrationDto registrationDto){
        loanService.addLoan(registrationDto);
        var location = UriComponentsBuilder.fromPath("/loans/" + registrationDto.getLoan()).build().toUri();
        return ResponseEntity.created(location).body(new Loan());
    }

}
