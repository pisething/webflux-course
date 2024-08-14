package com.piseth.java.school.webflux_playgroud.sec005.filter;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Order(1)
@Component
public class AuthenticationWebFilter implements WebFilter{
	
	@Autowired
	private FilterErrorHandler errorHandler;
	
	
	private static final Map<String, Category> TOKEN_CATEGORY_MAP = Map.of(
			"secret123",Category.STANDARD,
			"secret456", Category.PRIME
			);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		// TODO Auto-generated method stub
		String token = exchange.getRequest().getHeaders().getFirst("auth-token");
		if(Objects.nonNull(token) && TOKEN_CATEGORY_MAP.containsKey(token)) {
			exchange.getAttributes().put("category", TOKEN_CATEGORY_MAP.get(token));
			return chain.filter(exchange);
		}
		//return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
		return errorHandler.sendProblemDetail(exchange, HttpStatus.UNAUTHORIZED, "Set the valid token");
	}

}
