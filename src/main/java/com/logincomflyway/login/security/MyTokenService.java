package com.logincomflyway.login.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.logincomflyway.login.models.User;
import com.logincomflyway.login.services.exceptions.UnauthorizedException;

@Service
@Component
public class MyTokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	private final String issuer = "auth-api";
	
	private Instant expires_in;
	
	
	public String generateToken(User user) {
		
		try {
			this.expires_in = genExpirationDate();
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
								.withIssuer(this.issuer)
								.withSubject(user.getEmail())
								.withExpiresAt(getExpiresIn())
								.sign(algorithm);
			
			return token;
			
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error while generating token", e);
		}
	}
	
	
	public String validateJwtToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			return JWT.require(algorithm)
					.withIssuer(this.issuer)
					.build()
					.verify(token)
					.getSubject();
			
			
		} catch (JWTVerificationException e) {
			throw new UnauthorizedException("Token inválido ou expirado");
			//return "";
		}
	}
	
	
	private Instant genExpirationDate() {
		//Duração do token 
		return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
	}


	public Instant getExpiresIn() {
		return expires_in;
	}

}
