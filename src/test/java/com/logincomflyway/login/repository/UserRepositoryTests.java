package com.logincomflyway.login.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class UserRepositoryTests {
	
	
	@Autowired
	private UserRepository userRepository;
	
	private String existingEmail;
	private String notExistingEmail;
	
	//bob@gmail.com
	@BeforeEach
	public void setUp() {
		existingEmail = "bob@gmail.com";
		notExistingEmail = "mari@mari.com";
		
	}
	
	
	@Test
	public void findByEmailShouldReturnObjectWhenEmailExists() {
		UserDetails user = userRepository.findByEmail(existingEmail);
		
		//Assertions.assertNotNull(user);
		Assertions.assertEquals(existingEmail, user.getUsername());
	}
	
	@Test
	public void findByEmailShouldReturnNullWhenDoNotExistsEmail() {
		UserDetails user = userRepository.findByEmail(notExistingEmail);
		
		//Assertions.assertNotNull(user);
		Assertions.assertNull(user);
	}

	

}
