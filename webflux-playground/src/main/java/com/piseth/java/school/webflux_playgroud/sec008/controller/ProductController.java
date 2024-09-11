package com.piseth.java.school.webflux_playgroud.sec008.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.webflux_playgroud.sec008.dto.ProductDTO;
import com.piseth.java.school.webflux_playgroud.sec008.dto.UploadResponse;
import com.piseth.java.school.webflux_playgroud.sec008.service.ProductService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping(value = "upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<UploadResponse> uploadProducts(@RequestBody Flux<ProductDTO> flux){
		log.info("Invoked.");
		return productService.saveProducts(flux)
				.then(productService.getProductCount())
				.map(count -> new UploadResponse(UUID.randomUUID(), count));
	}

}