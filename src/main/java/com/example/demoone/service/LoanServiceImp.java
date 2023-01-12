package com.example.demoone.service;

import com.example.demoone.dto.RegistrationDto;
import com.example.demoone.entity.Collateral;
import com.example.demoone.entity.Customer;
import com.example.demoone.entity.Loan;
import com.example.demoone.exception.NotFoundException;
import com.example.demoone.repository.CollateralRepository;
import com.example.demoone.repository.CustomerRepository;
import com.example.demoone.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanServiceImp implements LoanService {
    private final LoanRepository loanRepository;
    private final CollateralRepository collateralRepository;
    private final CustomerRepository customerRepository;
    public LoanServiceImp(LoanRepository loanRepository,
                          CollateralRepository collateralRepository,
                          CustomerRepository customerRepository) {
        this.loanRepository = loanRepository;
        this.collateralRepository = collateralRepository;
        this.customerRepository = customerRepository;
    }
    @Override
    @Transactional
    public Loan register(RegistrationDto registrationDto) {
        var customerDto = registrationDto.getCustomer();
        var customer = new Customer(customerDto);
        customerRepository.save(customer);

        var loanDto = registrationDto.getLoan();
        var loan = new Loan(loanDto);
        loan.setCustomer(customer);
        loanRepository.save(loan);

        var collateralDtos = registrationDto.getCollaterals();
        for (var collateralDto : collateralDtos){
            var collateral = new Collateral(collateralDto);
            collateral.setLoan(loan);
            collateralRepository.save(collateral);
        }
        return loan;
    }
    @Override
    public Loan get(int id) {
        return loanRepository.findById(id).orElseThrow(() -> new NotFoundException("Loan not found"));
    }
}
