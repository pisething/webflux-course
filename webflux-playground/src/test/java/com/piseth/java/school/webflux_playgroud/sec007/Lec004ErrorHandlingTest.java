package com.piseth.java.school.webflux_playgroud.sec007;

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
public class Lec004ErrorHandlingTest extends AbstractWebClient{
	
	private WebClient client = createWebClient();

	
	//@Test
	void errorHandling(){
		this.client.get()
			.uri("demo002/lec004/calculator/{a}/{b}", 10,20)
			.header("operation", "@")
			.retrieve()
			.bodyToMono(CalculatorResponse.class)
			.doOnError(WebClientResponseException.class, ex -> log.info("{}", ex.getResponseBodyAs(ProblemDetail.class)))
			.onErrorReturn(new CalculatorResponse(0, 0, null, 0))
			//.onErrorReturn(WebClientResponseException.InternalServerError.class, new CalculatorResponse(0, 0, null, 0))
			//.onErrorReturn(WebClientResponseException.BadRequest.class, new CalculatorResponse(0, 0, null, 0))
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		
	}
	
	@Test
	void exchange(){
		this.client.get()
			.uri("demo002/lec004/calculator/{a}/{b}", 30,20)
			.header("operation", "+")
			.exchangeToMono(this::decode)
			.doOnNext(print())
			.then()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
		
	}
	
	private Mono<CalculatorResponse> decode(ClientResponse clientResponse){
		//clientResponse.cookies()
		//clientResponse.headers()
		
		log.info("status code {}", clientResponse.statusCode());
		if(clientResponse.statusCode().isError()) {
			return clientResponse.bodyToMono(ProblemDetail.class)
						.doOnNext(pd -> log.info("{}", pd))
						.then(Mono.empty());
		}
		
		return clientResponse.bodyToMono(CalculatorResponse.class);
		
	}
	
	
	
	
	
	
}
