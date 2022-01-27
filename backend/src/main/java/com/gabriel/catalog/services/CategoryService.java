package com.gabriel.catalog.services;

import java.util.List;
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
}
