package com.piseth.java.school.webflux_playgroud.sec005.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.piseth.java.school.webflux_playgroud.sec005.dto.CustomerDTO;
import com.piseth.java.school.webflux_playgroud.sec005.mapper.CustomerMapper;
import com.piseth.java.school.webflux_playgroud.sec005.repository.CustomerRepository;

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
	
	public Flux<CustomerDTO> getCustomers(int pageNumber, int pageSize){
		Pageable pageable = PageRequest.of(pageNumber -1, pageSize);
		return customerRepository.findBy(pageable)
				.map(customerMapper::toCustomerDTO);
	}
	
	public Mono<Page<CustomerDTO>> getCustomers2(int pageNumber, int pageSize){
		Pageable pageable = PageRequest.of(pageNumber -1, pageSize);
		return customerRepository.findBy(pageable)
				.map(customerMapper::toCustomerDTO)
				.collectList()
				.zipWith(customerRepository.count())
				.map(res -> new PageImpl<>(res.getT1(), pageable, res.getT2()));
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
	
	public Mono<Boolean> deleteCustomer(Integer customerId){
		return customerRepository.deleteCustomerById(customerId);
	}
}
