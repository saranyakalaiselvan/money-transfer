package com.transaction.moneytransfer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.transaction.moneytransfer.entity.Account;
import com.transaction.moneytransfer.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@MockBean
	private AccountService accountService;
	
	@InjectMocks
	private AccountController accountController;
	
	@Before
	public void setUp() {
        MockitoAnnotations.initMocks(this);
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
	      .apply(springSecurity()).build();
	}
	
	Account account = new Account();
 	List<Account> accounts = Arrays.asList();
 	String username = "user";
 	
 	@WithMockUser(authorities ={"CUSTOMER"})
	@Test
	public void getAccountsTest() throws Exception {
		when(accountService.getAccounts(username))
		.thenReturn(accounts);		
		this.mockMvc.perform(get("/account"))
				.andExpect(status().isOk());
	}
 	
 	@WithMockUser(authorities ={"CUSTOMER"})
	@Test
	public void saveAccountTest() throws Exception {
		when(accountService.saveAccount(username))
		.thenReturn(account);		
		this.mockMvc.perform(post("/account"))
				.andExpect(status().isCreated());
	}
 	
 	

}
