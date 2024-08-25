package com.piseth.java.school.webflux_playgroud.sec007.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorResponse {
	private int first;
	private int second;
	private String operation;
	private double result;

}
