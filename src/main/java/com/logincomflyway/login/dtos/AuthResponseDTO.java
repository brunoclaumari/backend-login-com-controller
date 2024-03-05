package com.logincomflyway.login.dtos;

import java.time.Instant;

import jakarta.persistence.Embeddable;

@Embeddable
public record AuthResponseDTO(String email, String access_token, Instant expires_in) {

}
