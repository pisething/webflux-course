package com.piseth.java.school.webflux_playgroud.sec007;

import java.util.function.Consumer;

import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractWebClient {
	
	protected <T> Consumer<T> print(){
		return item -> log.info("received: {}", item);
	}
	
	protected WebClient createWebClient() {
		return createWebClient(b -> {});
	}
	
	protected WebClient createWebClient(Consumer<WebClient.Builder> consumer) {
		var builder = WebClient.builder()
					.baseUrl("http://localhost:8080");
		
		consumer.accept(builder);
		
		return builder.build();
				
	}
}
