package com.piseth.java.school.webflux_playgroud.sec008.entity;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="product")
public class Product {
	private Integer id;
	private String description;
	private Integer price;
}
