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

@DataJpaTest // integration test
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private long existingId;
	private long absentId;

	@BeforeEach // learning, didn't really need to put it here as code repetition is not an
				// issue
	public void setUp() throws Exception {
		existingId = 1L;
		absentId = 9999999L;
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
}
