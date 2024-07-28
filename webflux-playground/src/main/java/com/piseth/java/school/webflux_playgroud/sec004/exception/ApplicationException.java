package com.piseth.java.school.webflux_playgroud.sec004.exception;

import reactor.core.publisher.Mono;

public class ApplicationException {
	
	// Generics, Functional Programming ,Stream API, Java Reactive, Spring Webflux
	
	public static <T> Mono<T> customerNotFound(int id){
		return Mono.error(new CustomerNotFoundException(id));
	}
	
	public static <T> Mono<T> missingName(){
		return Mono.error(new InvalidInputException("Name is required"));
	}
	
	public static <T> Mono<T> missingValidEmail(){
		return Mono.error(new InvalidInputException("Valid email is required"));
	}

}
