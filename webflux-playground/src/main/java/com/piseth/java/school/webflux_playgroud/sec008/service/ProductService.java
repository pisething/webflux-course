package com.piseth.java.school.webflux_playgroud.sec008.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piseth.java.school.webflux_playgroud.sec008.dto.ProductDTO;
import com.piseth.java.school.webflux_playgroud.sec008.mapper.ProductMapper;
import com.piseth.java.school.webflux_playgroud.sec008.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public Flux<ProductDTO> saveProducts(Flux<ProductDTO> flux){
		return flux.map(ProductMapper::toProduct)
				.as(this.repository::saveAll)
				.map(ProductMapper::toProductDTO);
				
	}
	
	public Mono<Long> getProductCount(){
		return repository.count();
	}
}
