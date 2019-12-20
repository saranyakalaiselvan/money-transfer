package com.transaction.moneytransfer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.transaction.moneytransfer.dao.UserRepository;
import com.transaction.moneytransfer.entity.Authority;
import com.transaction.moneytransfer.entity.User;
import com.transaction.moneytransfer.entity.UserInformation;
import com.transaction.moneytransfer.exception.BadRequestException;
import com.transaction.moneytransfer.exception.ResourceNotFoundException;
import com.transaction.moneytransfer.security.Role;

import static java.util.Collections.singleton;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User getUserByUserId(String userName) {
		return userRepository.findById(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User Name: "+userName+" doesn't exist. Please  enter valid User Id"));
	}

	public User saveUser(String userName, String password, String email, String phoneNumber)  {
		if (userRepository.existsById(userName)) {
			throw new BadRequestException("User: " + userName + " exists. Please  enter new User Name");
		}

		final User newUser = new User();
		newUser.setUsername(userName);
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setEnabled(true);
		final Authority authority = new Authority();
		authority.setUser(newUser);
		authority.setAuthority(Role.CUSTOMER.name());
		newUser.setAuthorities(singleton(authority));
		final UserInformation userInfo = setUserInfo(userName, email, phoneNumber, newUser);
		newUser.setUserInfo(userInfo);
		return userRepository.save(newUser);
	}

	private UserInformation setUserInfo(String userName, String email, String phoneNumber, final User newUser) {
		final UserInformation userInfo = new UserInformation();
		userInfo.setUsername(userName);
		userInfo.setUser(newUser);
		userInfo.setEmail(email);
		userInfo.setPhoneNumber(phoneNumber);
		return userInfo;
	}
	
}
