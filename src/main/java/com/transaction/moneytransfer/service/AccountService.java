package com.transaction.moneytransfer.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.moneytransfer.dao.AccountRepository;
import com.transaction.moneytransfer.dao.UserRepository;
import com.transaction.moneytransfer.entity.Account;
import com.transaction.moneytransfer.exception.ResourceNotFoundException;


@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountDao;
	
	@Autowired UserRepository userDao;

	public List<Account> getAccounts(String userName) {
		return userDao.findById(userName).map(user -> user.getUserInfo().getAccounts())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userName));	}

	public Account saveAccount(String userName) {
        return userDao.findById(userName).map(user -> {
            final Account account = new Account();
            account.setAccountNumber(UUID.randomUUID().toString());
            account.setUserInfo(user.getUserInfo());
            account.setBalance(BigDecimal.ZERO);
            account.setUsername(userName);
            return accountDao.save(account);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userName));
	}
	
}
