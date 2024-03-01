package com.logincomflyway.login.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.logincomflyway.login.dtos.AuthResponseDTO;
import com.logincomflyway.login.dtos.AuthenticationDTO;
import com.logincomflyway.login.dtos.RegisterDTO;
import com.logincomflyway.login.dtos.UserResponseDTO;
import com.logincomflyway.login.models.User;
import com.logincomflyway.login.repository.UserRepository;
import com.logincomflyway.login.security.TokenService;
import com.logincomflyway.login.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/myauth")
public class AuthenticationController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationService myAuthService;
	
	@Autowired
	TokenService tokenService;
	
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		var userNamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = this.authenticationManager.authenticate(userNamePassword);
		
		var token = tokenService.generateToken((User)auth.getPrincipal());
		
		AuthResponseDTO resp = new AuthResponseDTO(auth.getName(), token, null);
		
		return ResponseEntity.ok().body(resp);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid RegisterDTO data) {
		
		/*
		if(this.userRepository.findByEmail(data.email()) != null) 
		return ResponseEntity.badRequest().build();
		
		String encriptedPass = securityConfigurations.passwordEncoder().encode(data.password());
		//EnumRole role = EnumRole.valueOf(data.role());
		User newUser = new User(data.name(), data.email(), encriptedPass, data.role());
		
		this.userRepository.save(newUser);		 
		 * */	
		UserResponseDTO userResponseDTO = myAuthService.register(data);
		if(userResponseDTO.errorMessage() != null) //se retornou ID null, é porque já tem o usuário
			ResponseEntity.badRequest().body(userResponseDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userResponseDTO.email()).toUri();
		
		return ResponseEntity.created(uri).body(userResponseDTO);
	}
	

}
