package com.logincomflyway.login.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logincomflyway.login.dtos.RegisterDTO;
import com.logincomflyway.login.dtos.UserResponseDTO;
import com.logincomflyway.login.enums.EnumRole;
import com.logincomflyway.login.models.User;
import com.logincomflyway.login.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class AuthenticationService {
	
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public UserResponseDTO register(@Valid RegisterDTO data) {		
		
		User newUser = new User();
		if(this.userRepository.findByEmail(data.email()) != null) {			
			return new UserResponseDTO(null, null, null, String.format("Email %s já cadastrado para outro usuário", data.email()));
		}
		
		String encriptedPass = new BCryptPasswordEncoder().encode(data.password());
		//EnumRole role = EnumRole.valueOf(data.user_role());
		newUser = new User(data.name(), data.email(), encriptedPass, data.user_role());
		
		this.userRepository.save(newUser);
		
		return new UserResponseDTO(newUser.getName(), newUser.getEmail(), newUser.getUserRole().getRole(), null);
	}

}
