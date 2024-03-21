package com.aad.grocery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aad.grocery.model.ProductInventory;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Integer> {
	ProductInventory findByProductId(int productId);
	ProductInventory findByProductIdAndArchivedFalse(int productId);
	List<ProductInventory> findByArchivedFalse();
	ProductInventory updateProductByProductId(int productId, ProductInventory product);
	int deleteProductByProductId(int productId);
}
