package com.piseth.java.school.webflux_playgroud.sec008.mapper;

import com.piseth.java.school.webflux_playgroud.sec008.dto.ProductDTO;
import com.piseth.java.school.webflux_playgroud.sec008.entity.Product;

public class ProductMapper {

	public static Product toProduct(ProductDTO dto) {
		Product product = new Product();
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setId(dto.getId());
		return product;
	}
	
	public static ProductDTO toProductDTO(Product entity) {
		ProductDTO dto = new ProductDTO();
		dto.setDescription(entity.getDescription());
		dto.setPrice(entity.getPrice());
		dto.setId(entity.getId());
		return dto;
	}
}
