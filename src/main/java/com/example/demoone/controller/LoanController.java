package com.example.demoone.controller;


import com.example.demoone.dto.LoanSearchParams;
import com.example.demoone.dto.RegistrationDto;
import com.example.demoone.entity.Loan;
import com.example.demoone.service.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/register")
    public ResponseEntity <Loan> register(@RequestBody @Valid RegistrationDto registrationDto) {
        Loan registered = loanService.register(registrationDto);
        var location = UriComponentsBuilder.fromPath("/loans/{id}").buildAndExpand(registered.getId()).toUri();
        return  ResponseEntity.created(location).body(registered);
    }
    @GetMapping
    public Page<Loan> getLoans(@RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false, defaultValue = "10") int size,
                               @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction,
                               @RequestParam(required = false, defaultValue = "id")String field,
                               LoanSearchParams loanSearchParams)    {
        Sort sorter = Sort.by(direction, field);
        return loanService.getLoans(loanSearchParams, PageRequest.of(page, size, sorter));
        }
    @GetMapping("/{id}")
    public Loan get (@PathVariable int id){
        return loanService.get(id);
    }



}
