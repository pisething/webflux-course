package com.piseth.java.school.webflux_playgroud.sec005.filter;

import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Order(2)
@Component
public class AuthorizationWebFilter implements WebFilter{
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		// how to check if prime or standard
		Category category = exchange.getAttributeOrDefault("category", Category.STANDARD);
		return switch(category) {
		case PRIME -> prime(exchange, chain);
		case STANDARD -> standard(exchange, chain);
		};
	}
	
	private Mono<Void> prime(ServerWebExchange exchange, WebFilterChain chain) {
		return chain.filter(exchange);
	}
	
	private Mono<Void> standard(ServerWebExchange exchange, WebFilterChain chain) {
		HttpMethod method = exchange.getRequest().getMethod();
		boolean isGet = HttpMethod.GET.equals(method);
		if(isGet) {
			return chain.filter(exchange);
		}
		return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
	}
	
	

}
