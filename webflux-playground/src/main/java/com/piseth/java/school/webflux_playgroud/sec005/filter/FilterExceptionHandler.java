package com.piseth.java.school.webflux_playgroud.sec005.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@Component
public class FilterExceptionHandler {
	
	@Autowired
	private ServerCodecConfigurer codecConfigurer;
	private ServerResponse.Context context;
	
	@PostConstruct
	private void init() {
		this.context = new ContextImpl(codecConfigurer);
	}
	
	public Mono<Void> sendProblemDetail(ServerWebExchange webExchange, HttpStatus httpStatus, String message){
		var problem = ProblemDetail.forStatusAndDetail(httpStatus, message);
		return ServerResponse.status(httpStatus)
					.bodyValue(problem)
					.flatMap(sr -> sr.writeTo(webExchange, this.context));
	}
	
	private record ContextImpl(ServerCodecConfigurer configurer) implements ServerResponse.Context{

		@Override
		public List<HttpMessageWriter<?>> messageWriters() {
			
			return this.configurer.getWriters();
		}

		@Override
		public List<ViewResolver> viewResolvers() {
			
			return List.of();
		}
		
	}

}
