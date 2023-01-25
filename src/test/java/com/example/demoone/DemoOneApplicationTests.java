package com.example.demoone;


import com.example.demoone.entity.Customer;
import com.example.demoone.entity.Loan;
import com.example.demoone.entity.Post;
import com.example.demoone.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

import javax.persistence.Query;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void testSearchingLoans() throws Exception {
		var customer = new Customer("firstName", "lastName", "privateNumber");
		em.persistAndFlush(customer);
		em.refresh(customer);
		for (var i = 1; i < 20; i++) {
			var loan = new Loan("loanNumber", customer);
			em.persist(loan);
		}
		em.flush();

		mockMvc.perform(get("/loans")
						.param("size", "5")
						.param("page", "0"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()", equalTo(5)))
				.andExpect(jsonPath("$.content[0].createdDate", matchesPattern(".+")));
	}
    @Test
	void testAddingLoans() throws Exception {
        var customer = new Customer("firstName", "lastName", "privateNumber");
		var loan = new Loan("loanNumber", customer);
		var body = objectMapper.writeValueAsString(loan);
		mockMvc.perform(post("/loans")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().exists("location"))
				.andExpect(jsonPath("$.id", not(nullValue())));

	}
    @Test
	void testSearchingPosts() throws Exception {
		var user = new User("username", "password", "email");
		em.persistAndFlush(user);
		em.refresh(user);
		for (var i = 1; i < 20; i++) {
			var post = new Post("title", "body", user);
			em.persist(post);
		}
		em.flush();

		mockMvc.perform(get("/posts")
						.param("size", "5")
						.param("page", "0"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()", equalTo(5)))
				.andExpect(jsonPath("$.first", is(true)))
				.andExpect(jsonPath("$.content[0].createDate", matchesPattern(".+")));
	}
	@Test
	void testAddingPosts () throws Exception {
		var user = new User("username", "password", "email");
		var post= new Post ("title", "body", user);
		var body = objectMapper.writeValueAsString(post);
		mockMvc.perform(post("/posts")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().exists("location"))
				.andExpect(jsonPath("$.id", not(nullValue())));
		Query query = em.getEntityManager().createQuery("select p from Post p");
		List<Post> posts = query.getResultList();
		Assertions.assertEquals(1, posts.size());

	}

}
