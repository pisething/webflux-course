package com.piseth.java.school.webflux_playgroud.sec002.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.piseth.java.school.webflux_playgroud.sec002.Product;

import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer>{

	Flux<Product> findByPriceBetween(int start, int end);
}
