package com.piseth.java.school.external_service.controller;

import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.external_service.dto.CalculatorResponse;
import com.piseth.java.school.external_service.dto.Product;
import com.piseth.java.school.external_service.exception.CalculatorException;
import com.piseth.java.school.external_service.util.Transformer;
import com.piseth.java.school.external_service.util.Util;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("demo002")
@RequiredArgsConstructor
@Slf4j
public class WebClientController {
	
	private final Set<String> operations = Set.of("+", "-", "*", "/");
	  
	  @GetMapping({"lec004/calculator/{first}/{second}"})
	  @Operation(summary = "Calculator Service", description = "  Does the math operation and returns the response.\n  first and second parameter values should be > 0.\n  Send one of these \"+, -, *, /\" as header value.\n  Example: \"operation: +\"\n", tags = {"demo02"})
	  public Mono<CalculatorResponse> calculator(@PathVariable Integer first, @PathVariable Integer second, @RequestHeader Map<String, String> headers) {
	    String path = "/demo002/lec004/calculator/" + first + "/" + second;
	   
	    if (!headers.containsKey("operation") || !this.operations.contains(headers.get("operation"))) {
	    	 return Mono.error(() -> new CalculatorException("operation header should be + - * /"));
	    }
	    if (first.intValue() < 1) {
	    	return Mono.error(() -> new CalculatorException("first parameter should be > 0"));
	    }
	       
	    if (second.intValue() < 1) {
	    	 return Mono.error(() -> new CalculatorException("second parameter should be > 0"));
	    }
	      
	    switch ((String)headers.get("operation")) {
	      case "+" : return Mono.fromCallable(() -> new CalculatorResponse(first, second, "+", (double)first + second));
	      
	      case "-": return Mono.fromCallable(() -> new CalculatorResponse(first, second, "-", (double)first - second));
	      
	      case "*": return Mono.fromCallable(() ->new CalculatorResponse(first, second, "*", (double)first * second));
	      
	      case "/": return Mono.fromCallable(() ->new CalculatorResponse(first, second, "/", (double)first / second));
	      
	    } 
	    
	     return Mono.error(() -> new CalculatorException("something is wrong"));
	  }
	  
	  @GetMapping({"lec005/calculator"})
	  @Operation(summary = "Query Param Calculator Service", description = "  Does the math operation and returns the response. It expects 3 parameters.\n  </br><code>http://localhost:7070/demo02/lec06/calculator?first=10&second=20&operation=+</code>\n  </br>first and second parameter values should be > 0.\n  operation should be one of \"+, -, *, /\"\n", tags = {"demo02"})
	  public Mono<CalculatorResponse> calculateWithQueryParams(@RequestParam Integer first, @RequestParam Integer second, @RequestParam String operation) {
	    String path = "/demo002/lec005/calculator?first=%d&second=%d&operation=%s".formatted(new Object[] { first, second, operation });
	    log.info("{}", path);
	    if (!this.operations.contains(operation))
	      return Mono.error((Throwable)new CalculatorException("operation param should be + - * /")); 
	    if (first.intValue() < 1)
	      return Mono.error((Throwable)new CalculatorException("first parameter should be > 0")); 
	    if (second.intValue() < 1)
	      return Mono.error((Throwable)new CalculatorException("second parameter should be > 0")); 
	    switch (operation) {
	    case "+" : return Mono.fromCallable(() -> new CalculatorResponse(first, second, "+", (double)first + second));
	      
	      case "-": return Mono.fromCallable(() -> new CalculatorResponse(first, second, "-", (double)first - second));
	      
	      case "*": return Mono.fromCallable(() ->new CalculatorResponse(first, second, "*", (double)first * second));
	      
	      case "/": return Mono.fromCallable(() ->new CalculatorResponse(first, second, "/", (double)first / second));
	      
	    } 
	    return 
	      
	      Mono.error((Throwable)new CalculatorException("something is wrong"));
	  }
	  
	  @GetMapping({"lec006/product/{id}"})
	  @Operation(summary = "Basic Auth Product Service", description = "Provide product details for the given product id. It supports product id between 1 and 100.\n</br>It expects basic credentials to be sent. <code>username: java, password: secret</code>.\nreturns 401 otherwise!\n", tags = {"demo02"})
	  public Mono<ResponseEntity<Product>> getProduct(@PathVariable Integer id, @RequestHeader Map<String, String> headers) {
	    log.info("headers received: {}", headers);
	    if (headers.containsKey("Authorization") && (new String(Base64.getDecoder().decode(((String)headers.get("Authorization")).replace("Basic ", "")))).equals("java:secret")) {
	      String path = "/demo02/lec07/product/" + id;
	      if (id.intValue() < 1 || id.intValue() > 100)
	        return Mono.empty(); 
	      return Mono.delay(Duration.ofSeconds(1L))
	        .map(i -> new Product(id, "product-" + id, Util.faker().random().nextInt(10, 1000)))
	        .map(ResponseEntity::ok)
	        .transform(Transformer.monoLogger(path));
	    } 
	    return Mono.just(ResponseEntity.status(401).build());
	  }
	  
	  @GetMapping({"lec007/product/{id}"})
	  @Operation(summary = "Bearer Token Product Service", description = "Provide product details for the given product id. It supports product id between 1 and 100.\n</br>It expects bearer token to be sent. <code>Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9</code>\nreturns 401 otherwise!\n", tags = {"demo02"})
	  public Mono<ResponseEntity<Product>> bearerTokenProduct(@PathVariable Integer id, @RequestHeader Map<String, String> headers) {
	    log.info("headers received: {}", headers);
	    if (headers.containsKey("Authorization") && ((String)headers.get("Authorization")).replace("Bearer ", "").equals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")) {
	      String path = "/demo02/lec08/product/" + id;
	      if (id.intValue() < 1 || id.intValue() > 100)
	        return Mono.empty(); 
	      return Mono.delay(Duration.ofSeconds(1L))
	        .map(i -> new Product(id, "product-" + id, Util.faker().random().nextInt(10, 1000)))
	        .map(ResponseEntity::ok)
	        .transform(Transformer.monoLogger(path));
	    } 
	    return Mono.just(ResponseEntity.status(401).build());
	  }

}
