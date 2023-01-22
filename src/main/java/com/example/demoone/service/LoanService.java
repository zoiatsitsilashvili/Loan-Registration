package com.example.demoone.service;

import com.example.demoone.dto.LoanSearchParams;
import com.example.demoone.dto.RegistrationDto;
import com.example.demoone.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface LoanService {

    Loan register(RegistrationDto registrationDto);
    Loan get(int id);
    void updateInterest(Loan loan, LocalDateTime endTime);

    Page<Loan> getLoans(LoanSearchParams loanSearchParams, Pageable pageable);
}
