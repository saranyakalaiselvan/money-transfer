package com.transaction.moneytransfer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.transaction.moneytransfer.dao.AccountRepository;
import com.transaction.moneytransfer.dao.UserRepository;
import com.transaction.moneytransfer.entity.Account;
import com.transaction.moneytransfer.entity.UserInformation;

public class AccountServiceTest {
	
	@Mock
	private AccountRepository accountDao;
	
	@Mock
	private UserRepository userDao;
	
	@InjectMocks
	private AccountService accountService;

	private String accountNumber = "12345";

	private String username = "user";
	
	@Mock
	private UserInformation userInfo;

	private  BigDecimal balance = new BigDecimal(100.0);
	
	private Account account = new Account(accountNumber, username, userInfo, balance) ;
	
	private List<Account> accounts = Arrays.asList(account,account);

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetAccounts() {
		Mockito.when(accountDao.findAll())
		.thenReturn(accounts);
		List<Account> returnedAccounts = accountDao.findAll();
		assertEquals(2, returnedAccounts.size());
		Mockito.verify(accountDao,Mockito.times(1)).findAll();
	}

	@Test
	public void testSaveAccount() {
		Mockito.when(accountDao.save(account))
		.thenReturn(account);
		Account returnedAccount = accountDao.save(account);
		assertNotNull(returnedAccount.getAccountNumber());
		Mockito.verify(accountDao,Mockito.times(1)).save(account);
	}

}
