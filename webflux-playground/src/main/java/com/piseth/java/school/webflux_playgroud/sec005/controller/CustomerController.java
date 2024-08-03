package com.piseth.java.school.webflux_playgroud.sec005.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.webflux_playgroud.sec005.dto.CustomerDTO;
import com.piseth.java.school.webflux_playgroud.sec005.exception.ApplicationException;
import com.piseth.java.school.webflux_playgroud.sec005.filter.Category;
import com.piseth.java.school.webflux_playgroud.sec005.service.CustomerService;
import com.piseth.java.school.webflux_playgroud.sec005.validator.RequestValidator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping
	Flux<CustomerDTO> getAll(@RequestAttribute("category") Category category){
		System.out.println(category);
		return customerService.getAll();
	}
	
	@GetMapping("paginated")
	Flux<CustomerDTO> getCustomers(@RequestParam int number, @RequestParam int size){
		return customerService.getCustomers(number, size);
	}
	
	@GetMapping("paginated2")
	Mono<Page<CustomerDTO>> getCustomers2(@RequestParam int number, @RequestParam int size){
		return customerService.getCustomers2(number, size);
	}
	
	@GetMapping("{customerId}")
	Mono<CustomerDTO> getById(@PathVariable Integer customerId){
		return customerService.getById(customerId)
					.switchIfEmpty(ApplicationException.customerNotFound(customerId));
				
	}
	
	@PostMapping
	Mono<CustomerDTO> saveCustomer(@RequestBody Mono<CustomerDTO> mono){
		//var validatedRequest = mono.transform(RequestValidator.validate());
		//return customerService.saveCustomer(validatedRequest);
		return mono.transform(RequestValidator.validate())
					.as(customerService::saveCustomer);
	}
	
	@PutMapping("{customerId}")
	Mono<CustomerDTO> updateCustomer(@PathVariable Integer customerId, @RequestBody Mono<CustomerDTO> mono){
		return mono.transform(RequestValidator.validate())
				.as(validateReq -> customerService.updateCustomer(customerId, validateReq))
				.switchIfEmpty(ApplicationException.customerNotFound(customerId));
	}
	
	@DeleteMapping("{customerId}")
	Mono<Void> deleteById(@PathVariable Integer customerId){
		return customerService.deleteCustomer(customerId)
				.filter(b -> b) // if true
				.switchIfEmpty(ApplicationException.customerNotFound(customerId))
				.then();
		
	}
}
