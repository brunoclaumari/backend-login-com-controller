package com.logincomflyway.login.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.logincomflyway.login.models.User;
import com.logincomflyway.login.services.exceptions.UnauthorizedException;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	private final String issuer = "auth-api";
	
	
	public String generateToken(User user) {
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
								.withIssuer(this.issuer)
								.withSubject(user.getEmail())
								.withExpiresAt(genExpirationDate())
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
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

}
