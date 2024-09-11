package com.piseth.java.school.webflux_playgroud.sec008;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import com.piseth.java.school.webflux_playgroud.sec008.dto.ProductDTO;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class ProductUploadDownloadTest {
	
	private ProductClient productClient = new ProductClient();
	
	/*
	@Test
	public void upload() {
		var flux = Flux.just(new ProductDTO(null,"iphone",2000))
						.delayElements(Duration.ofSeconds(10));
		
		this.productClient.uploadProducts(flux)
			.doOnNext(e -> log.info("Received: {}",e))
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
	}
	*/
	
	/*
	@Test
	public void upload() {
		var flux = Flux.range(1, 10)
					.map(i -> new ProductDTO(null,"product-"+i,i))
						.delayElements(Duration.ofSeconds(2));
		
		this.productClient.uploadProducts(flux.doOnNext(e -> log.info("Received: {}",e)))
			.doOnNext(e -> log.info("Received: {}",e))
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
	}
	*/
	
	@Test
	public void upload() {
		var flux = Flux.range(1, 1_000_000)
					.map(i -> new ProductDTO(null,"product-"+i,i));
		
		this.productClient.uploadProducts(flux.doOnNext(e -> log.info("Received: {}",e)))
			.doOnNext(e -> log.info("Received: {}",e))
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
	}
}
