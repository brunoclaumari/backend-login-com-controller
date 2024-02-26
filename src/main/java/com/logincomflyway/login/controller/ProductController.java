package com.logincomflyway.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logincomflyway.login.models.Product;
import com.logincomflyway.login.repository.ProductRepository;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
	
	@Autowired
	private ProductRepository repository;

	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		
		var lista = repository.findAll();
		
		return ResponseEntity.ok().body(lista);
	}

}
