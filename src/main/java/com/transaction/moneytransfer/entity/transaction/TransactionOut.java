package com.transaction.moneytransfer.entity.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.transaction.moneytransfer.entity.Account;

@Entity
public class TransactionOut extends Transaction{
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_number")
    @JsonIgnore
    private Account toAccount;

    @Column(name = "inverse_transaction_id")
    private String inverseTransactionId;

	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}

	public String getInverseTransactionId() {
		return inverseTransactionId;
	}

	public void setInverseTransactionId(String inverseTransactionId) {
		this.inverseTransactionId = inverseTransactionId;
	}

}
