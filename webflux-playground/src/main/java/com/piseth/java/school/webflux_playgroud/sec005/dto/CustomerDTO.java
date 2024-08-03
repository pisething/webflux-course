package com.piseth.java.school.webflux_playgroud.sec005.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
	private Integer id;
	private String name;
	private String email;
}
