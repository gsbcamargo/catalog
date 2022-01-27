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
import com.gabriel.catalog.services.exceptions.EntityNotFoundException;

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
		Category category = categoryOptional
				.orElseThrow(() -> new EntityNotFoundException(String.format("Sorry, entity of id %s not found.", id)));
		return new CategoryDto(category);
	}
	
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
