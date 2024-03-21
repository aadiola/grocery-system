package com.aad.grocery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aad.grocery.model.ProductQuantitySales;
import com.aad.grocery.model.SalesTransaction;

public interface ProductQuantitySalesRepository extends JpaRepository<ProductQuantitySales, Integer> {
	List<ProductQuantitySales> findBySalesTransaction(SalesTransaction salesTransaction);
}
