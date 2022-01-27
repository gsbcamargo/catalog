package com.gabriel.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.catalog.dtos.CategoryDto;
import com.gabriel.catalog.entities.Category;
import com.gabriel.catalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDto> findAll() {
		List<Category> list = repository.findAll();

		return list.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDto findById(Long id) {
		Optional<Category> categoryOptional = repository.findById(id);
		Category category = categoryOptional.get();
		return new CategoryDto(category);
	}
}
