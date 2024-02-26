package com.logincomflyway.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logincomflyway.login.models.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

	
}
