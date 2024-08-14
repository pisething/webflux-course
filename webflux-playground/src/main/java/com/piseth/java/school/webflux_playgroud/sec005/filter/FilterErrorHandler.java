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
import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

@Component
public class FilterErrorHandler {
	
	@Autowired
	private ServerCodecConfigurer codecConfigurer;
	private ServerResponse.Context context;
	
	@PostConstruct
	private void init() {
		this.context = new ContextImpl(codecConfigurer);
	}
	
	public Mono<Void> sendProblemDetail(ServerWebExchange exchange, HttpStatus status, String message){
		var problem = ProblemDetail.forStatusAndDetail(status, message);
		
		return ServerResponse.status(status)
				.bodyValue(problem)
				.flatMap(sr -> sr.writeTo(exchange, context))
				;
	}
	
	@Data
	@AllArgsConstructor
	private class ContextImpl implements ServerResponse.Context{
		
		private ServerCodecConfigurer codecConfigurer;
		
		@Override
		public List<HttpMessageWriter<?>> messageWriters() {
			return this.codecConfigurer.getWriters();
		}

		@Override
		public List<ViewResolver> viewResolvers() {
			// TODO Auto-generated method stub
			return List.of();
		}
		
	}

}
