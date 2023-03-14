package com.gabriel.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.catalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
