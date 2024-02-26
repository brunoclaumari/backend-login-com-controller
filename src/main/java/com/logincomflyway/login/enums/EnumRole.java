package com.logincomflyway.login.enums;

public enum EnumRole {
	
	/*
	 * ADMIN: contem autorizações de ADMIN e as de USER
	 * USER: contem autorizações de USER
	 * */
	
	ADMIN("ADMIN"),
	USER("USER");
	
	private String role;
	
	EnumRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return this.role;
	}

}
