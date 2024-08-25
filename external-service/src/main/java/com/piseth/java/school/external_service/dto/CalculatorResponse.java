package com.piseth.java.school.external_service.dto;

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
