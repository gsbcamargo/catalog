package com.gabriel.catalog.dtos;

import java.io.Serializable;

import com.gabriel.catalog.entities.Category;

public class CategoryDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;

	public CategoryDto() {
	}

	public CategoryDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryDto(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
