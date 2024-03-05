package com.logincomflyway.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories
public class LoginApplication {

	public static void main(String[] args) {
		
		Logger logger = LoggerFactory.getLogger(LoginApplication.class);
		
		try {
			SpringApplication.run(LoginApplication.class, args);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Someone is wrong!! Check if database is up!!");
		}
		
	}

}
