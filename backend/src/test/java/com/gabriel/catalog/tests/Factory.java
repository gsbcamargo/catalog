package com.gabriel.catalog.tests;

import java.math.BigDecimal;
import java.time.Instant;

import com.gabriel.catalog.dtos.ProductDto;
import com.gabriel.catalog.entities.Category;
import com.gabriel.catalog.entities.Product;

public class Factory {

	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Just a test phone", new BigDecimal(999.99),
				"https://image.com/phoneImage", Instant.parse("2023-10-13T05:00:00Z"));
		product.getCategories().add(new Category(2L, "Electronics"));
		return product;
	}
	
	public static ProductDto createProductDto() {
		Product product = createProduct();
		return new ProductDto(product, product.getCategories());
	}
}
