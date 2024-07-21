package com.piseth.java.school.webflux_playgroud.sec003;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.piseth.java.school.webflux_playgroud.sec003.dto.CustomerDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec003")
public class CustomerServiceTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void getCustomers() {
		webTestClient.get()
			.uri("/customers")
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBodyList(CustomerDTO.class)
			.value(list -> log.info("{}",list))
			.hasSize(10);
	}
	
	@Test
	void getCustomersPagination() {
		webTestClient.get()
			.uri("/customers/paginated?number=3&size=2")
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectBody()
			.consumeWith(res -> log.info("{}", new String(res.getResponseBody())))
			.jsonPath("$.length()").isEqualTo(2)
			.jsonPath("$[0].id").isEqualTo(5)
			.jsonPath("$[1].id").isEqualTo(6);
	}
	
	@Test
	void getCustomerById() {
		webTestClient.get()
			.uri("/customers/1")
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectBody()
			.consumeWith(res -> log.info("{}", new String(Objects.requireNonNull(res.getResponseBody()))))
			.jsonPath("$.id").isEqualTo(1)
			.jsonPath("$.name").isEqualTo("sam")
			.jsonPath("$.email").isEqualTo("sam@gmail.com");
	}
	
	@Test
	void createAndDeleteCustomer() {
		// create
		var dto = new CustomerDTO();
		dto.setName("Piseth");
		dto.setEmail("piseth@gmail.com");
		webTestClient.post()
			.uri("/customers")
			.bodyValue(dto)
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectBody()
			.consumeWith(res -> log.info("{}", new String(Objects.requireNonNull(res.getResponseBody()))))
			.jsonPath("$.id").isEqualTo(11)
			.jsonPath("$.name").isEqualTo("Piseth")
			.jsonPath("$.email").isEqualTo("piseth@gmail.com");
		
		// delete
		webTestClient.delete()
			.uri("/customers/11")
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectBody().isEmpty();
		
		
	}
	
	@Test
	void updateCustomer() {
		var dto = new CustomerDTO();
		dto.setName("ethan");
		dto.setEmail("ethan@gmail.com");
		webTestClient.put()
			.uri("/customers/10")
			.bodyValue(dto)
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectBody()
			.consumeWith(res -> log.info("{}", new String(Objects.requireNonNull(res.getResponseBody()))))
			.jsonPath("$.id").isEqualTo(10)
			.jsonPath("$.name").isEqualTo("ethan")
			.jsonPath("$.email").isEqualTo("ethan@gmail.com");
		
	}
	
	@Test
	void customerNotFound() {
		//get
		webTestClient.get()
			.uri("/customers/11")
			.exchange()
			.expectStatus().is4xxClientError()
			.expectBody().isEmpty();
		
		// delete
		webTestClient.delete()
			.uri("/customers/11")
			.exchange()
			.expectStatus().is4xxClientError()
			.expectBody().isEmpty();
		
		// update
		var dto = new CustomerDTO();
		dto.setName("ethan");
		dto.setEmail("ethan@gmail.com");
		webTestClient.put()
			.uri("/customers/11")
			.bodyValue(dto)
			.exchange()
			.expectStatus().is4xxClientError()
			.expectBody().isEmpty();
			
	}
}

