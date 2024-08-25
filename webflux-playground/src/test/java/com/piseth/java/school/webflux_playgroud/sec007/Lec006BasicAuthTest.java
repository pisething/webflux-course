package com.piseth.java.school.webflux_playgroud.sec007;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.piseth.java.school.webflux_playgroud.sec007.dto.CalculatorResponse;
import com.piseth.java.school.webflux_playgroud.sec007.dto.Product;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class Lec006BasicAuthTest extends AbstractWebClient{
	
private WebClient client = createWebClient(b -> b.defaultHeaders(h -> h.setBasicAuth("java", "secret")));

	
	@Test
	void basicAuth(){
		this.client.get()
			.uri("demo002/lec006/product/2")
			.retrieve()
			.bodyToMono(Product.class)
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		
	}
	
	
}
