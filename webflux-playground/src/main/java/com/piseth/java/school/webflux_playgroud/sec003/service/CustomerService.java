package com.piseth.java.school.webflux_playgroud.sec003.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piseth.java.school.webflux_playgroud.sec003.dto.CustomerDTO;
import com.piseth.java.school.webflux_playgroud.sec003.entity.Customer;
import com.piseth.java.school.webflux_playgroud.sec003.mapper.CustomerMapper;
import com.piseth.java.school.webflux_playgroud.sec003.repository.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerMapper customerMapper;
	
	public Flux<CustomerDTO> getAll(){
		return customerRepository.findAll()
					.map(customerMapper::toCustomerDTO);
	}
	
	public Mono<CustomerDTO> getById(Integer customerId){
		return customerRepository.findById(customerId)
				.map(customerMapper::toCustomerDTO);
	}
	
	public Mono<CustomerDTO> saveCustomer(Mono<CustomerDTO> mono){
		return mono.map(customerMapper::toCustomer)
			.flatMap(customerRepository::save)
			.map(customerMapper::toCustomerDTO);
	}
	
	public Mono<CustomerDTO> updateCustomer(Integer customerId, Mono<CustomerDTO> mono){
		return customerRepository.findById(customerId)
			.flatMap(entity -> mono)
			.map(customerMapper::toCustomer)
			.doOnNext(c -> c.setId(customerId))
			.flatMap(customerRepository::save)
			.map(customerMapper::toCustomerDTO);
			
	}
	
	public Mono<Void> deleteCustomer(Integer customerId){
		return customerRepository.deleteById(customerId);
	}
}
