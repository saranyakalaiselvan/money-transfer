package com.transaction.moneytransfer.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

import com.transaction.moneytransfer.entity.User;
import com.transaction.moneytransfer.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private WebApplicationContext context;
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private Principal principal;
	
	@Mock
	private User user;
	
	private List<User> users = new ArrayList<User>();
	
	private MockMvc mockMvc;
	
	@Before
	public void testSetUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@Test
	@WithMockUser(authorities = {"ADMINISTRATOR"})
	public void testGetUsers() throws Exception {
		Mockito.when(userService.getUsers()).thenReturn(users);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/user"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(authorities = {"ADMINISTRATOR"})
	public void testGetUserByUserId() throws Exception {
		Mockito.when(userService.getUserByUserId(principal.getName()))
		.thenReturn(user);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/user/"+principal.getName()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testSaveUser() throws Exception {
		String password = "password";
		String email = "abc@test.com";
		String phoneNumber="123456789";
		String username = "test";
		Mockito.when(userService.saveUser(principal.getName(), password , email , phoneNumber))
		.thenReturn(user);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/user")
				.param("username", username)
				.param("password", password)
				.param("email", email)
				.param("phoneNumber", phoneNumber))
		.andExpect(MockMvcResultMatchers.status().isCreated());
	}

}
