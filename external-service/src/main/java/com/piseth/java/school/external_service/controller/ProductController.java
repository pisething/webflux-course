package com.piseth.java.school.external_service.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.external_service.dto.Product;
import com.piseth.java.school.external_service.service.ProductService;
import com.piseth.java.school.external_service.util.Transformer;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("demo001")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;
	
	@GetMapping("products")
	@Operation(summary = "Product Service", description = "Produce up to 10 item, each item takes 1 second. So, 10 products will take 10 seconds.",
	tags = {"demo001"})
	public Flux<Product> getProducts(){
		String path = "demo001/products";
		return productService.getProduct()
				.transform(Transformer.fluxLogger(path));
	}
	
	@GetMapping("products/black")
	@Operation(summary = "Product Service", description = "Produce up to 10 item, each item takes 1 second. So, 10 products will take 10 seconds.",
	tags = {"demo001"})
	public Flux<Product> getProductsWithError(){
		String path = "demo001/product";
		return productService.getProductWithError()
				.transform(Transformer.fluxLogger(path));
	}
	
	
	@GetMapping("products/{productId}")
	@Operation(summary = "Product Service", description = "Provide Product detail for the given product id, "
			+ "id is between 1 to 100, it will take 1 second for each.",
	tags = {"demo001"})
	public Mono<Product> getProduct(@PathVariable Integer productId){
		
		String path = "demo001/products/" + productId;
		if(productId < 1 || productId > 100) {
			return Mono.empty();
		}
		return productService.getProductById(productId)
				.transform(Transformer.monoLogger(path));
	}
	
	@GetMapping(value = "products/stream", produces = {"text/event-stream"})
	@Operation(summary = "Product Service", description = "Provide product detail as stream. 500ms delay for each item.",
	tags = {"demo001"})
	public Flux<Product> getProductStream(){
		String path = "demo001/products/stream";
		return productService.getProductStream()
				.transform(Transformer.fluxLogger(path));
	}
	
	@GetMapping("products/withheader/{productId}")
	@Operation(summary = "With Header - Product Service", description = "Provide product detail for given productId, have to provide header 'caller-id',"
			+ "Example: 'caller-id' : order-service",
	tags = {"demo001"})
	public Mono<ResponseEntity<Product>> getProductByIdWithHeader(@PathVariable Integer productId, @RequestHeader Map<String, String> headers){
		String path = "demo001/products/withheader" + productId;
		return productService.getProductByIdWithHeader(productId, headers)
				.transform(Transformer.monoLogger(path));
	}

}
