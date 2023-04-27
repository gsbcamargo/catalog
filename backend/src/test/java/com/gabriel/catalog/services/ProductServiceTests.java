package com.gabriel.catalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gabriel.catalog.repositories.ProductRepository;
import com.gabriel.catalog.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	// in order to perform a unit test, without the need to inject and use the repository
	// and the db access
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepository;
	
	private long existingId;
	private long inexistentId;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 13L;
		inexistentId = 99999L;
		
		Mockito.doNothing().when(productRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(inexistentId);
		
	}
	
	@Test
	public void deleteById_shouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			productService.deleteById(existingId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(inexistentId);
	}
	
	@Test
	public void deleteById_shouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.deleteById(inexistentId);
		});
		
		Mockito.verify(productRepository).deleteById(inexistentId); // TODO needs fix
	}
	
}
