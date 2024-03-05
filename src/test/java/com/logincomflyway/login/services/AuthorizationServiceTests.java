package com.logincomflyway.login.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.logincomflyway.login.repository.UserRepository;


@ExtendWith(SpringExtension.class)
public class AuthorizationServiceTests {
	
	@InjectMocks
	private AuthorizationService service;
	
	@Mock
	private UserRepository userRepository;
	
	private String existingEmail;
	private String notExistingEmail;
	
	
	@BeforeEach
	public void setUp() {
		existingEmail = "bob@gmail.com";
		notExistingEmail = "mari@mari.com";
		
	}
	

	@Test
	void test() {
		
	}

}
