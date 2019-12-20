package com.transaction.moneytransfer.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.moneytransfer.entity.transaction.Deposit;
import com.transaction.moneytransfer.entity.transaction.TransactionOut;
import com.transaction.moneytransfer.entity.transaction.Withdraw;
import com.transaction.moneytransfer.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/deposit")
    @ResponseStatus(code = HttpStatus.OK)
    public Deposit deposit(@RequestParam String accountNumber, @RequestParam BigDecimal amount,
                           Principal principal){
        return transactionService.depositTransaction(accountNumber,amount,principal.getName());
    }
    
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/withdraw")
    @ResponseStatus(code = HttpStatus.OK)
    public Withdraw withdraw(@RequestParam String accountNumber, @RequestParam BigDecimal amount,
                               Principal principal) {
    	return transactionService.withdrawTransaction(accountNumber,amount,principal.getName());
    }
    
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/transfer")
    @ResponseStatus(code = HttpStatus.OK)
    public TransactionOut transfer(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
                                @RequestParam BigDecimal amount, Principal principal) {
    	return transactionService.transferTransaction(fromAccountNumber,toAccountNumber,amount,principal.getName());
    	
    }

}
