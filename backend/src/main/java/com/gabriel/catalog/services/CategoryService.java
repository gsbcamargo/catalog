package com.gabriel.catalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.catalog.dtos.CategoryDto;
import com.gabriel.catalog.entities.Category;
import com.gabriel.catalog.repositories.CategoryRepository;
import com.gabriel.catalog.services.exceptions.DatabaseException;
import com.gabriel.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDto> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = repository.findAll(pageRequest);
		return list.map(x -> new CategoryDto(x));
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
	
	public void deleteById(Long id) {
		try {
			repository.deleteById(id);
		} 
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(String.format("Sorry, entity of id %s not found.", id));
		}
	}

	@Transactional
	public CategoryDto update(Long id, CategoryDto dto) {
		try {
			Category entity = repository.getById(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDto(entity);
		} 
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(String.format("Sorry, entity of id %s not found.", id));
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation.");
		}
	}
}
