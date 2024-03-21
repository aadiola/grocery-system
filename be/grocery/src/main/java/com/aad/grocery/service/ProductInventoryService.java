package com.aad.grocery.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aad.grocery.controllers.ProductInventoryController;
import com.aad.grocery.exceptions.InvalidProductDataException;
import com.aad.grocery.exceptions.ProductDoesNotExistException;
import com.aad.grocery.model.ProductInventory;
import com.aad.grocery.repositories.ProductInventoryRepository;

import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class ProductInventoryService {
	
	
	@Autowired
	private ProductInventoryRepository productInventoryRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private Validator validator;
	
	static Logger logger = LogManager.getLogger(ProductInventoryService.class.getName());
	
	public ProductInventory createProductInventory (ProductInventory product) {
		
		
		Set<ConstraintViolation<ProductInventory>> violations = validator.validate(product);
		
		for(ConstraintViolation<ProductInventory> violation : violations) {
			logger.error(violation.getMessage());
			throw new InvalidProductDataException(violation.getMessage());
			
		}
		
		return productInventoryRepository.save(new ProductInventory(
				product.getProductName(),
				product.getProductPrice(),
				product.getAvailableQuantity(),
				product.getExpirationDate(),
				product.getUnit(),
				product.getRemarks()
				));
		
	}
	
	public List<ProductInventory> getProductInventory () {
		return productInventoryRepository.findByArchivedFalse();
	}
	
	public ProductInventory getProductInventory (Integer productId) {

		Optional<ProductInventory> retVal = Optional.ofNullable(productInventoryRepository.findByProductIdAndArchivedFalse(productId));

		if(retVal.isEmpty()) {
			throw new ProductDoesNotExistException(productId);
		}
		return retVal.get();
	}
	
	
	public ProductInventory updateProductInventory (ProductInventory product) throws Exception {
		if(product.getProductId() == null) {
			throw new ProductDoesNotExistException(null);
		}
		
		Optional<ProductInventory> retVal = Optional.ofNullable(productInventoryRepository.findByProductIdAndArchivedFalse(product.getProductId()));
		if(retVal.isEmpty()) {
			throw new ProductDoesNotExistException(product.getProductId());
		}
		
		return productInventoryRepository.updateProductByProductId(product.getProductId(), product);
		
	}
	
	public int deleteProductInventory (Integer productId) throws Exception {
		
		if(productId == null) {
			throw new ProductDoesNotExistException(null);
		}
		
		Optional<ProductInventory> retVal = Optional.ofNullable(productInventoryRepository.findByProductIdAndArchivedFalse(productId));
		if(retVal.isEmpty()) {
			throw new ProductDoesNotExistException(productId);
		}
		return productInventoryRepository.deleteProductByProductId(productId);
		
	}
	
	
	
	
	
	

}
