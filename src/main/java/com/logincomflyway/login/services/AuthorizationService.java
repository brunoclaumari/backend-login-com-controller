package com.logincomflyway.login.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.logincomflyway.login.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(AuthorizationService.class);
	
	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails user = repository.findByEmail(username);
		if(user == null) {
			logger.error("User not found: " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		
		logger.info("User found: " + username);		
		
		return user;
	}
	
	

}
