package com.piseth.java.school.webflux_playgroud.sec007;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.piseth.java.school.webflux_playgroud.sec007.dto.Product;

import reactor.test.StepVerifier;

public class Lec003RequestHeaderTest extends AbstractWebClient{
	
	private WebClient client = createWebClient(b -> b.defaultHeader("caller-id", "order-service"));

	
	//@Test
	void defaultHeader(){
		this.client.get()
			.uri("demo001/products/withheader/1")
			.retrieve()
			.bodyToMono(Product.class)
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		
	}
	
	//@Test
	void overrideHeader(){
		this.client.get()
			.uri("demo001/products/withheader/1")
			.header("caller-id", "pisethService")
			.retrieve()
			.bodyToMono(Product.class)
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		
	}
	
	@Test
		void mapHeader(){
			var map = Map.of(
					"key-1","Dara",
					"key-2","23",
					"test","50"
					);
		
			this.client.get()
				.uri("demo001/products/withheader/1")
				.headers(h -> h.setAll(map))
				.retrieve()
				.bodyToMono(Product.class)
				.doOnNext(print())
				.then()
				.as(StepVerifier::create)
				.expectComplete()
				.verify();
			
		}
	
	
	
	
	
}
