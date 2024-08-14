package com.piseth.java.school.webflux_playgroud.sec006.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfiguration {
	
	@Autowired
	private CustomerRequestHandler handler;
	
	@Bean
	public RouterFunction<ServerResponse> customerRouters(){ 
		return RouterFunctions.route()
				.GET("customers", handler::allCustomers)
				.GET("customers/paginated", handler::paginatedCustomers)
				.GET("customers/{customerId}", handler::getCustomerById)
				.POST("customers", handler::saveCustomer)
				.PUT("customers/{customerId}", handler::updateCustomer)
				.DELETE("customers/{customerId}", handler::deleteCustomer)
				.build();
	}

}
