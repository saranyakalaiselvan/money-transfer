package com.transaction.moneytransfer.entity.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.transaction.moneytransfer.entity.Account;

@Entity(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type")
public class Transaction implements Serializable{
	
    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_date", nullable = false, updatable = false)
    private Date transactionDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_number", nullable = false)
    private Account account;

    @NotNull
    private BigDecimal amount;
    
	public Transaction() {
		super();
	}
	
	public Transaction(String transactionId, Date transactionDate, Account account, @NotNull BigDecimal amount) {
		super();
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.account = account;
		this.amount = amount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
    
}
