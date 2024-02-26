package com.logincomflyway.login.dtos;

import com.logincomflyway.login.enums.EnumRole;

public record RegisterDTO(String name, String email, String password, EnumRole user_role) {

}
