package com.logincomflyway.login.security;

import java.io.IOException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.logincomflyway.login.controller.exceptions.StandardError;
import com.logincomflyway.login.repository.UserRepository;
import com.logincomflyway.login.services.exceptions.UnauthorizedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private MyTokenService tokenService;
	
	@Autowired
	UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {		

		try {
			
			var uri = request.getRequestURI();
			String token = this.recoverToken(request);
			if(token != null) {
				String login = tokenService.validateJwtToken(token);
				UserDetails user = userRepository.findByEmail(login);
				
				if(user != null) {
					Authentication authentication = new UsernamePasswordAuthenticationToken(login, null, user.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);				
				}
			}
			//if(!"/h2-console/**".contains(uri) && !"/favicon.ico".contains(uri))
				filterChain.doFilter(request, response);			
			
		} catch (UnauthorizedException e) {
			// TODO: handle exception
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    response.setCharacterEncoding("UTF-8");
		    
		    response.getWriter().println(String.format("{\"mensagem\": \"%s\"}", e.getLocalizedMessage()));
		    
		    
		}
		
	}

	
	private String recoverToken(HttpServletRequest request) {
		
		var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(authHeader == null) return null;
		
		return authHeader.replace("Bearer ", "");
		
	}

}
