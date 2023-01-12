package com.example.demoone.controller;

import com.example.demoone.dto.RegistrationDto;
import com.example.demoone.entity.Loan;
import com.example.demoone.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/register")
    public ResponseEntity <Loan> register(@RequestBody RegistrationDto registrationDto) {
        Loan registered = loanService.register(registrationDto);
        var location = UriComponentsBuilder.fromPath("/loans/{id}").buildAndExpand(registered.getId()).toUri();
        return  ResponseEntity.created(location).body(registered);
    }

    @GetMapping("/{id}")
    public Loan get (@PathVariable int id){
        return loanService.get(id);
    }



}
