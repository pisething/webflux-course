package com.piseth.java.school.webflux_playgroud.sec007;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.piseth.java.school.webflux_playgroud.sec007.dto.CalculatorResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class Lec005QueryParamsTest extends AbstractWebClient{
	
	private WebClient client = createWebClient();

	
	@Test
	void uriBuilderVariable(){
		
		var path = "demo002/lec005/calculator";
		var query = "first={first}&second={second}&operation={operation}";
		
		this.client.get()
			.uri(builder -> builder.path(path).query(query).build(10,"+"))
			.retrieve()
			.bodyToMono(CalculatorResponse.class)
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		
	}
	
	//@Test
	void uriBuilderMap(){
		
		var path = "demo002/lec005/calculator";
		var query = "first={first}&second={second}&operation={operation}";
		var map = Map.of(
				"first",40,
				"second",20,
				"operation", "+"
				);
		
		this.client.get()
			.uri(builder -> builder.path(path).query(query).build(map))
			.retrieve()
			.bodyToMono(CalculatorResponse.class)
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		
	}
	
	
	
	
	
	
	
}
