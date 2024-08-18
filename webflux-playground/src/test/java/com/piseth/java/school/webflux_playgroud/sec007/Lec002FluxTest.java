package com.piseth.java.school.webflux_playgroud.sec007;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.piseth.java.school.webflux_playgroud.sec007.dto.Product;

import reactor.test.StepVerifier;

public class Lec002FluxTest extends AbstractWebClient{
	
	private WebClient client = createWebClient();

	
	//@Test
	void streaming(){
		this.client.get()
			.uri("demo001/products/stream")
			.retrieve()
			.bodyToFlux(Product.class)
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		
	}
	
	@Test
	void streaming2(){
		this.client.get()
			.uri("demo001/products/stream")
			.retrieve()
			.bodyToFlux(Product.class)
			.take(Duration.ofSeconds(3))
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		
	}
	
	
	
}
