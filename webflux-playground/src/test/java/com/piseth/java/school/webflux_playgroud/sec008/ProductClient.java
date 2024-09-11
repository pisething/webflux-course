package com.piseth.java.school.webflux_playgroud.sec008;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.piseth.java.school.webflux_playgroud.sec008.dto.ProductDTO;
import com.piseth.java.school.webflux_playgroud.sec008.dto.UploadResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductClient {

	private WebClient client = WebClient.builder()
			.baseUrl("http://localhost:7000")
			.build();
	
	public Mono<UploadResponse> uploadProducts(Flux<ProductDTO> flux){
		
		return this.client.post()
			.uri("products/upload")
			.contentType(MediaType.APPLICATION_NDJSON)
			.body(flux, ProductDTO.class)
			.retrieve()
			.bodyToMono(UploadResponse.class);
	}
}
