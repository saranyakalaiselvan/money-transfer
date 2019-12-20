package com.transaction.moneytransfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.moneytransfer.entity.User;
import com.transaction.moneytransfer.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService; 
	

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping
	public List<User> getUsers(){
		return userService.getUsers();
	}
	
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/{username}")
	public User getUserByUserId(@PathVariable String username) {
		return userService.getUserByUserId(username);
	}
	
	@PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
	public User saveUser(@RequestParam String username, @RequestParam String password,
            @RequestParam String email, @RequestParam String phoneNumber){
		return userService.saveUser(username,password,email,phoneNumber);
	}
}
