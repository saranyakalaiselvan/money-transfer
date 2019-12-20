package com.transaction.moneytransfer.dao;

import org.springframework.data.repository.CrudRepository;

import com.transaction.moneytransfer.entity.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

}
