package com.transaction.moneytransfer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transaction.moneytransfer.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

}
