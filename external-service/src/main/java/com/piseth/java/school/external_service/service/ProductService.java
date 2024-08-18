package com.piseth.java.school.external_service.service;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.piseth.java.school.external_service.dto.Product;
import com.piseth.java.school.external_service.util.Util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProductService {
	
	public Flux<Product> getProduct(){
		return Flux.range(1, 10)
					.delayElements(Duration.ofSeconds(1))
					.map(x -> new Product(x, "product_"+x, x));
	}
	
	public Flux<Product> getProductWithError(){
		return Flux.range(1, 10)
					.delayElements(Duration.ofSeconds(1))
					.take(4)
					.map(x -> new Product(x, "product_"+x, x))
					.concatWith(Mono.fromRunnable(() -> System.exit(1)));
	}
	
	public Mono<Product> getProductById(Integer id){
		return Mono.delay(Duration.ofSeconds(1))
				.map(x -> x.intValue() + id)
				.map(x -> new Product(x, "product_"+x, Util.faker().random().nextInt(1, 100)));
	}
	
	public Flux<Product> getProductStream(){
		return Flux.range(1, 10)
					.map(x -> new Product(x, "product_"+x, Util.faker().random().nextInt(1, 100)))
					.delayElements(Duration.ofMillis(500));
	}
	
	public Mono<ResponseEntity<Product>> getProductByIdWithHeader(Integer id, Map<String, String> headers){
		if(!headers.containsKey("caller-id")) {
			return Mono.just(ResponseEntity.badRequest().build());
		}
		log.info("headers: {}", headers);
		return Mono.delay(Duration.ofSeconds(1))
				.map(x -> x.intValue() + id)
				.map(x -> new Product(x, "product_"+x, Util.faker().random().nextInt(1, 100)))
				.map(ResponseEntity::ok);
	}

}
