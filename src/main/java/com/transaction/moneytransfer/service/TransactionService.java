package com.transaction.moneytransfer.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.moneytransfer.dao.AccountRepository;
import com.transaction.moneytransfer.dao.TransactionRepository;
import com.transaction.moneytransfer.dao.UserInformationRepository;
import com.transaction.moneytransfer.entity.Account;
import com.transaction.moneytransfer.entity.UserInformation;
import com.transaction.moneytransfer.entity.transaction.Deposit;
import com.transaction.moneytransfer.entity.transaction.TransactionIn;
import com.transaction.moneytransfer.entity.transaction.TransactionOut;
import com.transaction.moneytransfer.entity.transaction.Withdraw;
import com.transaction.moneytransfer.exception.BadRequestException;
import com.transaction.moneytransfer.exception.ResourceNotFoundException;

@Service
public class TransactionService {
	
	@Autowired
	private AccountRepository accountDao;
	
	@Autowired
	private TransactionRepository transactionDao;
	
	@Autowired
	private UserInformationRepository userInfoDao;
	
    @Transactional
    public Deposit deposit(final Account account, final BigDecimal amount) {
        saveDepositAccountInfo(account, amount);
        return saveDeposit(account, amount);
    }

    @Transactional
    public Withdraw withdraw(final Account account, final BigDecimal amount) {
    	saveWithdrawAccountInfo(account, amount);
        return saveWithdraw(account, amount);
    }

    @Transactional
    public TransactionOut transfer(final Account fromAccount, final Account toAccount,
                                final BigDecimal amount) {
    	
        final Date transactionDate = new Date();
        final TransactionOut transactionOut = new TransactionOut();
        final TransactionIn transactionIn = new TransactionIn();
        final String transferOutTransactionId = UUID.randomUUID().toString();
        final String transferInTransactionId = UUID.randomUUID().toString();

    	saveWithdrawAccountInfo(fromAccount, amount);
        saveDepositAccountInfo(toAccount, amount);

        saveTransactionOut(fromAccount, toAccount, amount, transactionDate, transactionOut, transferOutTransactionId);
        saveTransactionIn(fromAccount, toAccount, amount, transactionDate, transactionIn, transferOutTransactionId,
				transferInTransactionId);

        transactionOut.setInverseTransactionId(transferInTransactionId);
        return transactionDao.save(transactionOut);
    }
    
	private void saveDepositAccountInfo(final Account account, final BigDecimal amount) {
		account.setBalance(account.getBalance().add(amount));
        accountDao.save(account);
	}

	private Deposit saveDeposit(final Account account, final BigDecimal amount) {
		final Deposit deposit = new Deposit();
        deposit.setTransactionId(UUID.randomUUID().toString());
        deposit.setAmount(amount);
        deposit.setAccount(account);
        deposit.setTransactionDate(new Date());
        return transactionDao.save(deposit);
	}
	
	private Withdraw saveWithdraw(final Account account, final BigDecimal amount) {
		final Withdraw withdrawal = new Withdraw();
        withdrawal.setTransactionId(UUID.randomUUID().toString());
        withdrawal.setAmount(amount);
        withdrawal.setAccount(account);
        withdrawal.setTransactionDate(new Date());
        return transactionDao.save(withdrawal);
	}

	private void saveWithdrawAccountInfo(final Account account, final BigDecimal amount) {
		account.setBalance(account.getBalance().subtract(amount));
        accountDao.save(account);
	}

	private void saveTransactionIn(final Account fromAccount, final Account toAccount, final BigDecimal amount,
			final Date transactionDate, final TransactionIn transactionIn, final String transferOutTransactionId,
			final String transferInTransactionId) {
		transactionIn.setTransactionId(transferInTransactionId);
        transactionIn.setTransactionDate(transactionDate);
        transactionIn.setAccount(toAccount);
        transactionIn.setAmount(amount);
        transactionIn.setFromAccount(fromAccount);
        transactionIn.setInverseTransactionId(transferOutTransactionId);
        transactionDao.save(transactionIn);
	}

	private void saveTransactionOut(final Account fromAccount, final Account toAccount, final BigDecimal amount,
			final Date transactionDate, final TransactionOut transactionOut, final String transferOutTransactionId) {
		transactionOut.setTransactionId(transferOutTransactionId);
        transactionOut.setTransactionDate(transactionDate);
        transactionOut.setAccount(fromAccount);
        transactionOut.setAmount(amount);
        transactionOut.setToAccount(toAccount);
        transactionDao.save(transactionOut);
	}

	public Deposit depositTransaction(String accountNumber, BigDecimal amount, String userName)  {
		return userInfoDao.findById(userName)
                .map(userInfo -> deposit(userInfo, accountNumber, amount))
                .orElseThrow(() -> new ResourceNotFoundException("User Information doesn't exists :"+userName+". Please enter valid user name"));		
	}
    
    private Deposit deposit(UserInformation userInfo, String accountNumber, BigDecimal amount) {
        return accountDao.findByAccountNumberAndUserInfo(accountNumber, userInfo)
                .map(account -> deposit(account, amount))
                .orElseThrow(() -> new BadRequestException(String.format("Account not found :"+accountNumber+". Please enter valid account details")));
    }

	public Withdraw withdrawTransaction(String accountNumber, BigDecimal amount, String userName) {
        final Optional<UserInformation> userInfoOptional = fetchUserInformation(userName);
        final Optional<Account> accountOptional = getAccountInfoByUserInfo(accountNumber, userInfoOptional);
        final Account account = accountOptional.get();
        if (account.getBalance().compareTo(amount) >= 0) {
            return withdraw(account, amount);
        } else {
            throw new BadRequestException("Insufficient balance");
        }
	}

	public TransactionOut transferTransaction(String fromAccountNumber, String toAccountNumber, BigDecimal amount,
			String userName) {
        final Optional<UserInformation> userInfoOptional = fetchUserInformation(userName);
        final Optional<Account> fromAccountOptional = getAccountInfoByUserInfo(fromAccountNumber, userInfoOptional);
        final Optional<Account> toAccountOptional = accountDao.findById(toAccountNumber);
        if (!toAccountOptional.isPresent()) {
            throw new BadRequestException(String.format("Account not found :"+toAccountNumber+". Please enter valid account details"));
        }
        final Account fromAccount = fromAccountOptional.get();
        if (fromAccount.getBalance().compareTo(amount) >= 0) {
            return transfer(fromAccount, toAccountOptional.get(), amount);
        } else {
            throw new BadRequestException("Insufficient balance");
        }
	}

	private Optional<Account> getAccountInfoByUserInfo(String fromAccountNumber,
			final Optional<UserInformation> userInfoOptional) {
		final Optional<Account> fromAccountOptional =
        		accountDao.findByAccountNumberAndUserInfo(fromAccountNumber, userInfoOptional.get());
        if (!fromAccountOptional.isPresent()) {
            throw new BadRequestException(String.format("Account not found :"+fromAccountNumber+". Please enter valid account details"));
        }
		return fromAccountOptional;
	}

	private Optional<UserInformation> fetchUserInformation(String userName) {
		final Optional<UserInformation> userInfoOptional = userInfoDao.findById(userName);
        if (!userInfoOptional.isPresent()) {
            throw new ResourceNotFoundException("User Information doesn't exists :"+userName+". Please enter valid user name");
        }
		return userInfoOptional;
	}
}
