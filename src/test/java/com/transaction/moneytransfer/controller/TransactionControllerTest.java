package com.transaction.moneytransfer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.transaction.moneytransfer.entity.Account;
import com.transaction.moneytransfer.entity.transaction.Deposit;
import com.transaction.moneytransfer.entity.transaction.TransactionOut;
import com.transaction.moneytransfer.entity.transaction.Withdraw;
import com.transaction.moneytransfer.service.TransactionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionControllerTest {
	
	private String accountNumber = "ABCDE";
	
	private String fromAccount = "ABCDE";

	private String toAccount = "DEFGH";
	
	private BigDecimal amount = new BigDecimal(100);
	
	@Mock
	List<Account> accounts;
	
	@Mock
	private Deposit deposit;
	
	@Mock
	private Withdraw withdraw;
	
	@Mock
	private Account account;
	
	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	private TransactionService transactionService;
	
	@Mock
	private TransactionOut transactionOut;
	
	@Mock
	private Principal principal;
	
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private TransactionController transactionController;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(authorities = {"CUSTOMER"})
	public void testDeposit() throws Exception {
		when(transactionService.deposit(account, amount)).thenReturn(deposit);
		this.mockMvc.perform(post("/transaction/deposit")
				.param("accountNumber",accountNumber)
				.param("amount",amount.toPlainString()))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = {"CUSTOMER"})
	public void testWithdraw() throws Exception {
		when(transactionService.withdraw(account, amount)).thenReturn(withdraw);
		this.mockMvc.perform(post("/transaction/withdraw")
				.param("accountNumber",accountNumber)
				.param("amount",amount.toString()))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = {"CUSTOMER"})
	public void testTransfer() throws JsonProcessingException, Exception {
		when(transactionService.transferTransaction(fromAccount, toAccount, amount,principal.getName()))
		.thenReturn(transactionOut);
		this.mockMvc.perform(post("/transaction/transfer")
				.param("fromAccountNumber", fromAccount)
				.param("toAccountNumber",toAccount)
				.param("amount", amount.toString()))
				.andExpect(status().isOk());
	}

}
