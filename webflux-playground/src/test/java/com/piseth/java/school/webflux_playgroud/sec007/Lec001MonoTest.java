package com.piseth.java.school.webflux_playgroud.sec007;

import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.piseth.java.school.webflux_playgroud.sec007.dto.Product;

public class Lec001MonoTest extends AbstractWebClient{
	
	private WebClient client = createWebClient();

	// simple GET
	
	//@Test
	void simpleGET() throws InterruptedException {
		this.client.get()
			.uri("demo001/products/1")
			.retrieve()
			.bodyToMono(Product.class)
			.doOnNext(print())
			.subscribe();
		
		Thread.sleep(Duration.ofSeconds(2));
	}
	
	//@Test
	void concurrentRequest() throws InterruptedException {
		
		for(int i=1; i <= 100; i++) {
			this.client.get()
			//.uri("demo001/products/{id}", i)
			.uri("{lec}/products/{id}", "demo001", i)
			.retrieve()
			.bodyToMono(Product.class)
			.doOnNext(print())
			.subscribe();
		}
		
		
		
		Thread.sleep(Duration.ofSeconds(2));
	}
	
	@Test
	void uriVariable() throws InterruptedException {
		var map = Map.of(
				"lec","demo001",
				"id",2
				);
		
		for(int i=1; i <= 100; i++) {
			this.client.get()
			//.uri("demo001/products/{id}", i)
			.uri("{lec}/products/{id}", map) 
			.retrieve()
			.bodyToMono(Product.class)
			.doOnNext(print())
			.subscribe();
		}
		
		
		
		Thread.sleep(Duration.ofSeconds(2));
	}
	
}
