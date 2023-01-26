package com.example.demoone.service;

import com.example.demoone.dto.LoanSearchParams;
import com.example.demoone.dto.RegistrationDto;
import com.example.demoone.entity.*;
import com.example.demoone.exception.NotFoundException;
import com.example.demoone.repository.CollateralRepository;
import com.example.demoone.repository.CustomerRepository;
import com.example.demoone.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.Duration;
import java.time.LocalDateTime;
@Slf4j
@Service
public class LoanServiceImp implements LoanService {
    private final LoanRepository loanRepository;
    private final CollateralRepository collateralRepository;
    private final CustomerRepository customerRepository;

    public LoanServiceImp(LoanRepository loanRepository,
                          CollateralRepository collateralRepository,
                          CustomerRepository customerRepository, RestTemplateBuilder restTemplateBuilder) {
        this.loanRepository = loanRepository;
        this.collateralRepository = collateralRepository;
        this.customerRepository = customerRepository;
    }
    @Override
    @Transactional
    public Loan register(RegistrationDto registrationDto) {
        var customerDto = registrationDto.getCustomer();
        if(customerDto.getPrivateNumber() == null){
            throw new IllegalArgumentException("Customer not found");
        }
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
    @Scheduled(fixedRate = 60 * 1000)
    public void calculateInterest() {
        loanRepository.findAll().forEach(loan -> updateInterest(loan, LocalDateTime.now()));
    }
    @Override
    public void updateInterest(Loan loan, LocalDateTime endTime) {
        var interestRate = loan.getInterestRate();
        var dailyInterestRate = interestRate / 365;
        long timeDiff = Math.abs(Duration.between(loan.getCreatedAt(), endTime).toMinutes());
        var interest = loan.getAmount() * dailyInterestRate / (24 * 60) * timeDiff;
        loan.setInterest(interest);
        loanRepository.save(loan);
    }
    @Override
    public Page<Loan> getLoans(LoanSearchParams loanSearchParams, Pageable pageable) {
        return loanRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if(loanSearchParams.getPrivateNumber() != null) {
                predicate = cb.and(predicate, cb.equal(root.get(Customer_.PRIVATE_NUMBER), loanSearchParams.getPrivateNumber()));
            }
            if(loanSearchParams.getId() != null){
                predicate = cb.and(predicate, cb.equal(root.get(Loan_.ID), loanSearchParams.getId()));
            }
            Join <Loan, Customer> customer = root.join(Customer_.FIRST_NAME, JoinType.LEFT);
            if(StringUtils.isNotEmpty(loanSearchParams.getFirstName())){
                predicate = cb.and(predicate, cb.like(customer.get(Customer_.FIRST_NAME), '%' + loanSearchParams.getFirstName() + '%'));
            }
            if(StringUtils.isNotEmpty(loanSearchParams.getLastName())){
                predicate = cb.and(predicate, cb.like(customer.get(Customer_.LAST_NAME), '%' + loanSearchParams.getLastName() + '%'));
            }
            if(loanSearchParams.getLoanNumber() != null){
                predicate = cb.and(predicate, cb.like(root.get(Loan_.LOAN_NUMBER), loanSearchParams.getLoanNumber()));
            }
            if(loanSearchParams.getBirthDate() != null){
               predicate = cb.and(predicate, cb.equal(customer.get(Customer_.BIRTH_DATE), loanSearchParams.getBirthDate()));
            }
            if(loanSearchParams.getCreatedAt() != null){
                var createdAt = loanSearchParams.getCreatedAt().atStartOfDay();
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get(Loan_.CREATED_AT), createdAt));
            }
            if (loanSearchParams.getUpdateAt() != null){
                var updatedAt = loanSearchParams.getUpdateAt().atTime(23,59,59);
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get(Loan_.UPDATED_AT), updatedAt));
            }
          //  if (loanSearchParams.getType() != null){
          //      Join<Loan,Collateral> collateralJoin = root.join(Collateral_.TYPE, JoinType.LEFT);
          //      predicate = cb.and(predicate, cb.(root.get(Collateral_.TYPE), loanSearchParams.getType()));
          //  }

            return predicate;
        }, pageable);
    }
}
