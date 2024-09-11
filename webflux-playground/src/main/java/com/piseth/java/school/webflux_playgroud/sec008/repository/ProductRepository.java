package com.piseth.java.school.webflux_playgroud.sec008.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.piseth.java.school.webflux_playgroud.sec008.entity.Product;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer>{

}
