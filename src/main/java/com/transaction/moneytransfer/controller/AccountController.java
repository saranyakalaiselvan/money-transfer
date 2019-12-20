package com.transaction.moneytransfer.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.moneytransfer.entity.Account;
import com.transaction.moneytransfer.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
    @PreAuthorize("hasAuthority('CUSTOMER')")
	@GetMapping
	public List<Account> getAccounts(Principal principal) {
		return accountService.getAccounts(principal.getName());
	}

    @PreAuthorize("hasAuthority('CUSTOMER')")
	@PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
	public Account saveAccount(Principal principal) {
		return accountService.saveAccount(principal.getName());
	}
}
