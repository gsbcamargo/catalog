package com.gabriel.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.catalog.dtos.CategoryDto;
import com.gabriel.catalog.entities.Category;
import com.gabriel.catalog.repositories.CategoryRepository;
import com.gabriel.catalog.services.exceptions.ResourceNotFoundException;

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
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Sorry, entity of id %s not found.", id)));
		return new CategoryDto(category);
	}
	
	@Transactional(readOnly = true)
	public CategoryDto insert(CategoryDto dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		
		return new CategoryDto(entity);
	}
	
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Transactional
	public CategoryDto update(Long id, CategoryDto dto) {
		
		try {
			Category entity = repository.getById(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			
			return new CategoryDto(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(String.format("Sorry, entity of id %s not found.", id));
		}
	}
}
