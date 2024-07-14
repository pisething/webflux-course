package com.piseth.java.school.webflux_playgroud.sec003.mapper;

import org.springframework.stereotype.Component;

import com.piseth.java.school.webflux_playgroud.sec003.dto.CustomerDTO;
import com.piseth.java.school.webflux_playgroud.sec003.entity.Customer;

@Component
public class CustomerMapper {
	
	public Customer toCustomer(CustomerDTO dto) {
		Customer customer = new Customer();
		customer.setName(dto.getName());
		customer.setEmail(dto.getEmail());
		return customer;
	}
	
	public CustomerDTO toCustomerDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(customer.getId());
		dto.setName(customer.getName());
		dto.setEmail(customer.getEmail());
		return dto;
	}
}
