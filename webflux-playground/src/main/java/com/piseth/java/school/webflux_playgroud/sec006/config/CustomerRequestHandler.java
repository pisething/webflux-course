package com.piseth.java.school.webflux_playgroud.sec006.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.piseth.java.school.webflux_playgroud.sec006.dto.CustomerDTO;
import com.piseth.java.school.webflux_playgroud.sec006.exception.ApplicationException;
import com.piseth.java.school.webflux_playgroud.sec006.service.CustomerService;
import com.piseth.java.school.webflux_playgroud.sec006.validator.RequestValidator;

import reactor.core.publisher.Mono;

@Component
public class CustomerRequestHandler {
	
	@Autowired
	private CustomerService customerService;

	public Mono<ServerResponse> allCustomers(ServerRequest request){
		
		return customerService.getAll()
				.as(flux -> ServerResponse.ok().body(flux, CustomerDTO.class));
				
	}
	
	public Mono<ServerResponse> paginatedCustomers(ServerRequest request){
		Integer size = request.queryParam("size").map(Integer::parseInt).orElse(3);
		Integer page = request.queryParam("page").map(Integer::parseInt).orElse(1);
		return customerService.getCustomers(page, size)
				.collectList()
				.as(flux -> ServerResponse.ok().body(flux, CustomerDTO.class));
				
	}
	
	public Mono<ServerResponse> getCustomerById(ServerRequest request){
		Integer customerId = Integer.parseInt(request.pathVariable("customerId"));
		return customerService.getById(customerId)
				.switchIfEmpty(ApplicationException.customerNotFound(customerId))
				.flatMap(ServerResponse.ok()::bodyValue);
	}
	
	public Mono<ServerResponse> saveCustomer(ServerRequest request){
		return request.bodyToMono(CustomerDTO.class)
				.transform(RequestValidator.validate())
				.as(this.customerService::saveCustomer)
				.flatMap(ServerResponse.ok()::bodyValue);
	}
	
	public Mono<ServerResponse> updateCustomer(ServerRequest request){
		Integer customerId = Integer.parseInt(request.pathVariable("customerId"));
		return request.bodyToMono(CustomerDTO.class)
				.transform(RequestValidator.validate())
				.as(validatedReq -> this.customerService.updateCustomer(customerId, validatedReq))
				.switchIfEmpty(ApplicationException.customerNotFound(customerId))
				.flatMap(ServerResponse.ok()::bodyValue);
	}
	
	public Mono<ServerResponse> deleteCustomer(ServerRequest request){
		Integer customerId = Integer.parseInt(request.pathVariable("customerId"));
		return customerService.deleteCustomer(customerId)
				.filter(b -> b)
				.switchIfEmpty(ApplicationException.customerNotFound(customerId))
				.then(ServerResponse.ok().build());
	}
	
}
