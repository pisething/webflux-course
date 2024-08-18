package com.piseth.java.school.external_service.util;

import java.util.function.UnaryOperator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class Transformer {

	public static <T> UnaryOperator<Flux<T>> fluxLogger(String path){
		return flux -> flux
				.doFirst(() -> log.info("Received: "+ path))
				.doOnCancel(() -> log.info("Cancelled: " + path))
				.doOnComplete(() -> log.info("Completed: " + path));
	}
	
	public static <T> UnaryOperator<Mono<T>> monoLogger(String path){
		return flux -> flux
				.doFirst(() -> log.info("Received: "+ path))
				.doOnCancel(() -> log.info("Cancelled: " + path))
				.doOnSuccess((item) -> log.info("Completed: " + item));
	}
}
