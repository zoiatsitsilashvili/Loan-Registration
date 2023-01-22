package com.example.demoone.repository;

import com.example.demoone.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository <Loan, Integer>, JpaSpecificationExecutor<Loan> {

}
