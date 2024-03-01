package com.logincomflyway.login.dtos;

import jakarta.persistence.Embeddable;

@Embeddable
public record AuthenticationDTO(String email, String password) {

}
