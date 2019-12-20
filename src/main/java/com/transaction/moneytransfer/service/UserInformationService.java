package com.transaction.moneytransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.moneytransfer.dao.UserInformationRepository;
import com.transaction.moneytransfer.entity.UserInformation;
import com.transaction.moneytransfer.exception.ResourceNotFoundException;

@Service
public class UserInformationService {
	
	@Autowired
	private UserInformationRepository userInfoDao;
	
	public UserInformation getUserByUserId(String userName){
		return userInfoDao.findById(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User Information doesn't exists: "+userName+". Please enter valid user name") );
	}

	public UserInformation updateUserProfile(String userName, String email, String phoneNumber){
		return userInfoDao.findById(userName)
				.map(userProfile -> {
			        userProfile.setEmail(email);
			        userProfile.setPhoneNumber(phoneNumber);
			        return userInfoDao.save(userProfile);
			    }).orElseThrow(() -> new ResourceNotFoundException("User Information doesn't exists: "+userName+". Please enter valid user name"));
	}
	
}
