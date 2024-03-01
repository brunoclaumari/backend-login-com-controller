package com.logincomflyway.login.dtos;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserResponseDTO(String name, String email, String user_role, String errorMessage) {

}
