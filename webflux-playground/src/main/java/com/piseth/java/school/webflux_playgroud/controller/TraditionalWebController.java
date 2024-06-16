package com.piseth.java.school.webflux_playgroud.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.piseth.java.school.webflux_playgroud.dto.Product;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("traditional")
@Slf4j
public class TraditionalWebController {
	
	private final RestClient restClient = RestClient
			.builder()
			.baseUrl("http://localhost:8080")
			.build();
	
	@GetMapping("products")
	public List<Product> getProducts(){
		var list = this.restClient.get()
			//.uri("/demo001/products")
			.uri("/demo001/products/black")
			.retrieve()
			.body(new ParameterizedTypeReference<List<Product>>() {
			});
		log.info("Received Response: {}", list);
		return list;
	}
	
	@GetMapping("products2")
	public Flux<Product> getProducts2(){
		var list = this.restClient.get()
			//.uri("/demo001/products")
			.uri("/demo001/products/black")
			.retrieve()
			.body(new ParameterizedTypeReference<List<Product>>() {
			});
		log.info("Received Response: {}", list);
		return Flux.fromIterable(list);
	}
	
	

}
