package com.piseth.java.school.webflux_playgroud.sec005.exception;

public class CustomerNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1665399558961778181L;
	private static final String MESSAGE = "Customer [id=%d] is not found";
	
	public CustomerNotFoundException(int customerId) {
		super(MESSAGE.formatted(customerId));
	}

}
