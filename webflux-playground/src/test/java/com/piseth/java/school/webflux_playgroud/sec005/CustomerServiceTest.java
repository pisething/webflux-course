package com.piseth.java.school.webflux_playgroud.sec005;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.piseth.java.school.webflux_playgroud.sec005.dto.CustomerDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec005")
public class CustomerServiceTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void unauthorized() {
		// no token
		webTestClient.get()
			.uri("/customers")
			.exchange()
			.expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
		
		// invalid token
		webTestClient.get()
			.uri("/customers")
			.header("auth-token", "secret888")
			.exchange()
			.expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
		
	}
	
	@Test
	void standard() {
		validateGet("secret123", HttpStatus.OK);
		validatePost("secret123", HttpStatus.FORBIDDEN);
	}
	
	@Test
	void prime() {
		validateGet("secret456", HttpStatus.OK);
		validatePost("secret456", HttpStatus.OK);
	}
	
	private void validateGet(String token, HttpStatus expectedStatus) {
		webTestClient.get()
			.uri("/customers")
			.header("auth-token", token)
			.exchange()
			.expectStatus().isEqualTo(expectedStatus);
	}
	
	private void validatePost(String token, HttpStatus expectedStatus) {
		var dto = new CustomerDTO(null, "Piseth", "piseth@gmail.com");
		webTestClient.post()
			.uri("/customers")
			.header("auth-token", token)
			.bodyValue(dto)
			.exchange()
			.expectStatus().isEqualTo(expectedStatus);
	}
	
	
}

