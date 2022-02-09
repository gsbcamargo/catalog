package com.gabriel.catalog.dtos;

import java.io.Serializable;
import java.time.Instant;

import com.gabriel.catalog.entities.Category;

public class CategoryDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Instant createdAt;
	private Instant updatedAt;

	public CategoryDto() {
	}

	public CategoryDto(Long id, String name, Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public CategoryDto(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
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

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}
