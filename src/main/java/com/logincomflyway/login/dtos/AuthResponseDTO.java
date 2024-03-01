package com.logincomflyway.login.dtos;

import jakarta.persistence.Embeddable;

@Embeddable
public record AuthResponseDTO(String email, String token, String expires) {

}
