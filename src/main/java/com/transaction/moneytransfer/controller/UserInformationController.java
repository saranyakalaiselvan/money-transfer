package com.transaction.moneytransfer.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.moneytransfer.entity.UserInformation;
import com.transaction.moneytransfer.service.UserInformationService;

@RestController
@RequestMapping("/userinfos")
public class UserInformationController {
	
	@Autowired
	private UserInformationService userInfoService;
	
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping
    public UserInformation getMyUserProfile(Authentication authentication, Principal principal){
        return userInfoService.getUserByUserId(principal.getName());
    }
    
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public UserInformation updateUserProfile(@RequestParam String email, @RequestParam String phoneNumber,
                                         Principal principal){
        return userInfoService.updateUserProfile(principal.getName(),email,phoneNumber);
    }

}
