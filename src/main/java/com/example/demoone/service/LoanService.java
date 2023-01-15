package com.example.demoone.service;

import com.example.demoone.dto.RegistrationDto;
import com.example.demoone.entity.Loan;
import org.springframework.stereotype.Service;

@Service
public interface LoanService {

    Loan register(RegistrationDto registrationDto);
    Loan get(int id);
    void updateInterest(Loan loan);
}
