package com.piseth.java.school.webflux_playgroud.sec005.validator;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.piseth.java.school.webflux_playgroud.sec005.dto.CustomerDTO;
import com.piseth.java.school.webflux_playgroud.sec005.exception.ApplicationException;

import reactor.core.publisher.Mono;

public class RequestValidator {

	public static UnaryOperator<Mono<CustomerDTO>> validate(){
		
		return mono -> mono.filter(hasName())
					.switchIfEmpty(ApplicationException.missingName())
					.filter(hasValidEmail())
					.switchIfEmpty(ApplicationException.missingValidEmail());
	}
	
	private static Predicate<CustomerDTO> hasName(){
		return dto -> Objects.nonNull(dto.getName());
	}
	
	private static Predicate<CustomerDTO> hasValidEmail(){
		return dto -> Objects.nonNull(dto.getEmail()) && dto.getEmail().contains("@");
	}
	
	
}
