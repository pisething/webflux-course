package com.piseth.java.school.webflux_playgroud.sec008.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	private Integer id;
	private String description;
	private Integer price;
}
