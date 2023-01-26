package com.example.demoone.service;

import com.example.demoone.dto.RegistrationDto;
import com.example.demoone.entity.Collateral;
import com.example.demoone.entity.Customer;
import com.example.demoone.repository.CollateralRepository;
import com.example.demoone.repository.CustomerRepository;
import com.example.demoone.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@SpringBootTest
@Transactional
class LoanServiceImpTest {
    @Autowired
    private LoanServiceImp loanServiceImp;

    @MockBean
    private LoanRepository loanRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private CollateralRepository collateralRepository;
    @Test
    public void testLoanRegister(){

        var customerDto = new RegistrationDto.Customer(
                "0101010101","nana", "narsia", LocalDate.of(2000,1,1));
        var loanDto = new RegistrationDto.Loan("12345678", 1000.0, 12, 12.0);
        var collateralDto = new RegistrationDto.Collateral(Collateral.CollateralType.CAR, 1000.0);
        var registrationDto = new RegistrationDto(loanDto, customerDto, of(collateralDto, collateralDto));
        //Loan loan2 = new Loan(loanDto);
       // when(loanRepository.save(loan2)).thenReturn(loan2);
        var loan = loanServiceImp.register(registrationDto);
        assertNotNull(loan);
        assertEquals(loanDto.getAmount(), loan.getAmount());
        assertNotNull(loan.getCustomer());

        verify(loanRepository, times(1)).save(loan);
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(collateralRepository, times(2)).save(any());

    }
    @Test
    public void testLoanRegister_when_private_number_is_null() {

        var customerDto = new RegistrationDto.Customer(
                null, "nana", "narsia", LocalDate.of(2000, 1, 1));
        var loanDto = new RegistrationDto.Loan("12345678", 1000.0, 12, 12.0);
        var collateralDto = new RegistrationDto.Collateral(Collateral.CollateralType.CAR, 1000.0);
        var registrationDto = new RegistrationDto(loanDto, customerDto, of(collateralDto, collateralDto));
        assertThrows(IllegalArgumentException.class, () -> loanServiceImp.register(registrationDto));
    }

}