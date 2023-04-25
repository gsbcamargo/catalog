package com.gabriel.catalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.gabriel.catalog.entities.Product;
import com.gabriel.catalog.tests.Factory;

@DataJpaTest // integration test
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private long existingId;
	private long absentId;
	private int countTotalProducts;

	@BeforeEach // learning, didn't really need to put it here as code repetition is not an
				// issue yet
	public void setUp() throws Exception {
		existingId = 1L;
		absentId = 9999999L;
		countTotalProducts = 25; // there are 25 products already seeded into db
	}

	@Test
	public void delete_shouldDeleteObjectWhenIdIsPresent(TestReporter testReporter) {
		// arrange, act, assert (AAA)

		long startTime = System.nanoTime();

		repository.deleteById(existingId);

		Optional<Product> result = repository.findById(existingId);

		Assertions.assertFalse(result.isPresent());

		long endTime = System.nanoTime();

		long elapsedTimeMillisconds = (endTime - startTime) / 1_000_000;

		testReporter.publishEntry("Elapsed time", elapsedTimeMillisconds + " milliseconds"); // learning

	}

	@Test
	public void delete_shouldReturnEmptyResultDataAccessExceptionWhenIdIsAbsent()
			throws EmptyResultDataAccessException {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(absentId);
		});
	}

	@Test
	public void insert_shouldPersistWithAutoincrementWhenIdIsNull() {

		Product product = Factory.createProduct();
		product.setId(null);

		product = repository.save(product);
		countTotalProducts += 1;

		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts, product.getId());

	}

	@Test
	public void findById_shouldReturnNonEmptyOptionalOfProductWhenIdIsPresent() {
		Optional<Product> result = repository.findById(existingId);

		Assertions.assertTrue(result.isPresent());
	}

	@Test
	public void findById_shouldReturnEmptyOptionalOfProductWhenIdIsAbsent() {
		Optional<Product> result = repository.findById(absentId);

		Assertions.assertTrue(result.isEmpty());
	}
}
