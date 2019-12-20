package com.transaction.moneytransfer.dao;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.transaction.moneytransfer.entity.Account;
import com.transaction.moneytransfer.entity.UserInformation;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, String>{
    Optional<Account> findByAccountNumberAndUserInfo(String accountNumber, UserInformation userInfo);
}
