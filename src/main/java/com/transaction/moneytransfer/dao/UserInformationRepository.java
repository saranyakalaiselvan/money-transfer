package com.transaction.moneytransfer.dao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.transaction.moneytransfer.entity.UserInformation;

@Repository
public interface UserInformationRepository extends CrudRepository<UserInformation, String> {

}
