package com.transaction.moneytransfer.controller;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.transaction.moneytransfer.entity.UserInformation;
import com.transaction.moneytransfer.service.UserInformationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInformationControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@MockBean
	private UserInformationService userInfoService;
	
	@InjectMocks
	private UserInformationController UserInformationController;
	
	@Mock
	private Principal principal;
	
	private UserInformation userInformation = new UserInformation();
	
	private String userName = "user";
	
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
		
	}

	@Test
	@WithMockUser(authorities = {"CUSTOMER"})
	public void testGetMyUserProfile() throws Exception {
		Mockito.when(userInfoService.getUserByUserId(userName))
		.thenReturn(userInformation);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/userinfos"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(userInfoService,Mockito.times(1)).getUserByUserId(userName);
	}

	@Test
	@WithMockUser(authorities = {"CUSTOMER"})
	public void testUpdateUserProfile() throws Exception {
		String email = "abc@test.com";
		String phoneNumber = "123456789";
		Mockito.when(userInfoService.updateUserProfile(userName, email , phoneNumber))
		.thenReturn(userInformation);
		this.mockMvc.perform(MockMvcRequestBuilders.put("/userinfos")
				.param("email", email)
				.param("phoneNumber", phoneNumber))
		.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(userInfoService,Mockito.times(1)).updateUserProfile(userName, email, phoneNumber);
	}

}
