package com.piseth.java.school.webflux_playgroud.sec002;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.piseth.java.school.webflux_playgroud.sec002.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;

@Slf4j
public class CustomerRepositoryTest extends AbstractTest{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Test
	void testFindAll() {
		
		StepVerifier.create(customerRepository.findAll())
			.expectNextCount(10)
			.expectComplete()
			.verify();
			
	}
	
	@Test
	void testFindAll2() {
		
		StepVerifier.create(customerRepository.findAll())
			.consumeNextWith(t -> {
				Assertions.assertEquals("sam", t.getName());
			})
			.expectNextCount(9)
			.expectComplete()
			.verify();
			
	}
	
	@Test
	void findByName() {
		customerRepository.findByName("sophia")
			.as(f -> StepVerifier.create(f))
			.assertNext(t -> Assertions.assertEquals("sophia@example.com", t.getEmail()))
			.expectComplete()
			.verify();
	}
	
	@Test
	void findByNameStartingWith() {
		customerRepository.findByNameStartingWith("e")
			.doOnNext(t -> log.info("Received: {}",t))
			.as(StepVerifier::create)
			.assertNext(t -> Assertions.assertEquals("emily", t.getName()))
			.assertNext(t -> Assertions.assertEquals("ethan", t.getName()))
			.expectComplete()
			.verify();
	}
	
	@Test
	void insertAndDeleteCustomer() {
		Customer customer = new Customer();
		customer.setName("Piseth");
		customer.setEmail("piseth@gmail.com");
		// Insert
		customerRepository.save(customer)
			.as(StepVerifier::create)
			.assertNext(t -> Assertions.assertNotNull(t.getId()))
			.expectComplete()
			.verify();
		
		customerRepository.count()
			.as(StepVerifier::create)
			.assertNext(t -> Assertions.assertEquals(11, t))
			.expectComplete()
			.verify();
		
		
		// Delete
		customerRepository.deleteById(11)
			.then(customerRepository.count())
			.as(StepVerifier::create)
			.assertNext(t -> Assertions.assertEquals(10, t))
			.expectComplete()
			.verify();
			
		
	}
	
	@Test
	void updateCustomer() {
		customerRepository.findById(4)
			.doOnNext(t -> t.setName("Dara")) // update
			.flatMap(t -> customerRepository.save(t)) // saving
			.as(StepVerifier::create)
			.assertNext(t -> Assertions.assertEquals("Dara", t.getName()))
			.expectComplete()
			.verify();
	}
	
	
}
