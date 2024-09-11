package com.piseth.java.school.webflux_playgroud.sec007;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.piseth.java.school.webflux_playgroud.sec007.dto.CalculatorResponse;
import com.piseth.java.school.webflux_playgroud.sec007.dto.Product;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class Lec008ExchangeFilterFunctionTest extends AbstractWebClient{
	
private WebClient client = createWebClient(b -> b.filter(tokenGenerator())
												 .filter(requestLogger()));

	/*
	@Test
	void exchangeFilter(){
		this.client.get()
			.uri("demo002/lec008/product/3")
			.retrieve()
			.bodyToMono(Product.class)
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		
	}
	*/
	@Test
	void exchangeFilter(){
		for(int i=1; i<6; i++) {
			this.client.get()
			.uri("demo002/lec008/product/{id}",i)
			.attribute("enable-logger", i%2 == 0)
			.retrieve()
			.bodyToMono(Product.class)
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		}
		
		
	}
	
	private ExchangeFilterFunction tokenGenerator() {
		return (request, next) ->{
			
			var token = UUID.randomUUID().toString().replace("-", "");
			log.info("generated token: {}", token);
			//request.headers().setBearerAuth(token);
			var modifiedRequest = ClientRequest.from(request).headers(h -> h.setBearerAuth(token)).build();
			
			return next.exchange(modifiedRequest);
		};
	}
	
	private ExchangeFilterFunction requestLogger() {
		return (request, next) ->{
			
			var enableLog = (Boolean)request.attributes().getOrDefault("enable-logger", false);
			if(enableLog) {
				log.info(" ====> Request URL: {}, {}", request.method(), request.url());
			}
			
			
			return next.exchange(request);
		};
	}
	
	
	
	
}
