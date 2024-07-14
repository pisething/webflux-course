package com.piseth.java.school.webflux_playgroud.sec002;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.piseth.java.school.webflux_playgroud.sec002.repository.CustomerOrderRepository;
import com.piseth.java.school.webflux_playgroud.sec002.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;

@Slf4j
public class CustomerOrderRepositoryTest extends AbstractTest{
	
	@Autowired
	private CustomerOrderRepository customerOrderRepository;
	
	@Test
	void findAllProductByCustomerName() {
		
		customerOrderRepository.findAllProductsByCustomerName("mike")
			.doOnNext(t -> log.info("Received: {}",t))
			.as(StepVerifier::create)
			.assertNext(t -> assertEquals("iphone 20", t.getDescription()))
			.assertNext(t -> assertEquals("mac pro", t.getDescription()))
			//.expectNextCount(2)
			.expectComplete()
			.verify();
			;
	}
	
	@Test
	void findOrderDetailByProduct() {
		customerOrderRepository.findOrderDetailByProductName("iphone 20")
			.doOnNext(t -> log.info("Received: {}",t))
			.as(StepVerifier::create)
			.assertNext(t -> assertEquals("sam", t.customerName()))
			.assertNext(t -> assertEquals("mike", t.customerName()))
			//.expectNextCount(2)
			.expectComplete()
			.verify();
			;
	}
	
	

}
