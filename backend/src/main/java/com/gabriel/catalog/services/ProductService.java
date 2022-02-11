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
import com.gabriel.catalog.dtos.ProductDto;
import com.gabriel.catalog.entities.Category;
import com.gabriel.catalog.entities.Product;
import com.gabriel.catalog.repositories.CategoryRepository;
import com.gabriel.catalog.repositories.ProductRepository;
import com.gabriel.catalog.services.exceptions.DatabaseException;
import com.gabriel.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDto> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = productRepository.findAll(pageRequest);
		return list.map(x -> new ProductDto(x));
	}

	@Transactional(readOnly = true)
	public ProductDto findById(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		Product product = productOptional.orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sorry, entity of id %s not found.", id)));
		return new ProductDto(product);
	}

	@Transactional
	public ProductDto insert(ProductDto dto) {
		Product entity = new Product();
		toDto(dto, entity);
		entity = productRepository.save(entity);
		return new ProductDto(entity);
	}

	public void deleteById(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(String.format("Sorry, entity of id %s not found.", id));
		}
	}

	@Transactional
	public ProductDto update(Long id, ProductDto dto) {
		try {
			Product entity = productRepository.getById(id);
			toDto(dto, entity);
			entity = productRepository.save(entity);
			return new ProductDto(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(String.format("Sorry, entity of id %s not found.", id));
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation.");
		}
	}

	private void toDto(ProductDto dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.setDate(dto.getDate());

		entity.getCategories().clear();
		for (CategoryDto catDto : dto.getCategories()) {
			Category category = categoryRepository.getById(catDto.getId());
			entity.getCategories().add(category);
		}
	}
}
