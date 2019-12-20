package com.transaction.moneytransfer.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "user_information")
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
public class UserInformation extends DateEntity{
	
    @Id
    private String username;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "username", nullable = false)
    @JsonIgnore
    private User user;

    
    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @Column(name = "phone_number")
    @Size(max = 15)
    private String phoneNumber;

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Account> accounts;
    
	public UserInformation() {
		super();
	}

	public UserInformation(String username, User user, @Email @NotNull String email, @Size(max = 15) String phoneNumber,
			List<Account> accounts) {
		super();
		this.username = username;
		this.user = user;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.accounts = accounts;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInformation other = (UserInformation) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
    
}
