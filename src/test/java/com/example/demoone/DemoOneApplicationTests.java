package com.example.demoone;


import com.example.demoone.entity.Customer;
import com.example.demoone.entity.Loan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@SpringBootTest
@Transactional
class DemoOneApplicationTests {
@Autowired
	private MockMvc mockMvc;

    @Autowired
	private TestEntityManager em;
	@Test
	void contextLoads() {
	}
	@Test
	void testSearchingLoans() throws Exception {
		var customer = new Customer("firstName", "lastName", "privateNumber", "birthDate");
		em.persistAndFlush(customer);
		em.refresh(customer);
		var loan = new Loan("loanNumber", customer);
		em.persistAndFlush(loan);

		mockMvc.perform(get("/loans")
				.param("size", "5")
				.param("page", "0"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()", equalTo(5)));


	}


}
