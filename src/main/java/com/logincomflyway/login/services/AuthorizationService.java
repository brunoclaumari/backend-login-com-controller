package com.logincomflyway.login.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.logincomflyway.login.models.User;
import com.logincomflyway.login.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	
	
	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*
		 User user = repository.findByEmail(username);
		if(user == null) {
			logger.error("User not found: " + username);
			throw new MyUsernameNotFoundException("Email not found");
		}
		
		logger.info("User found: " + username);
		return user;
		 * */
		UserDetails user = repository.findByEmail(username);
		
		return user;
	}
	
	

}
