package com.gabriel.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.catalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
