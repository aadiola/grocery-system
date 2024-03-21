package com.aad.grocery.repositories;

import org.springframework.stereotype.Repository;

import com.aad.grocery.exceptions.ProductDoesNotExistException;
import com.aad.grocery.model.ProductInventory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@Repository
@Transactional
public class ProductInventoryRepositoryImpl {
	
	@PersistenceContext
    private EntityManager entityManager;
	

	public ProductInventory updateProductByProductId(int productId, ProductInventory updatedProduct) {
		ProductInventory product = entityManager.find(ProductInventory.class, productId); 
		if(product != null) {
			product.setProductName(updatedProduct.getProductName());
			product.setProductPrice(updatedProduct.getProductPrice());
			product.setAvailableQuantity(updatedProduct.getAvailableQuantity());
			product.setExpirationDate(updatedProduct.getExpirationDate());
			product.setUnit(updatedProduct.getUnit());
			product.setRemarks(updatedProduct.getRemarks());
			product.setArchived(false);
			return entityManager.merge(product);
			
		}
		throw new ProductDoesNotExistException(productId);
	}
	
	public int deleteProductByProductId(int productId) {
		ProductInventory product = entityManager.find(ProductInventory.class, productId); 
		if(product != null) {
			product.setArchived(true);
			entityManager.merge(product);
			return 1;
			
		}
		throw new ProductDoesNotExistException(productId);
	}
	
}
