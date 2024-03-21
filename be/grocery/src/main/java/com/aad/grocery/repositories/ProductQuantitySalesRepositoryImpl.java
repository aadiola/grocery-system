package com.aad.grocery.repositories;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ProductQuantitySalesRepositoryImpl {
	
	@PersistenceContext
    private EntityManager entityManager;

}
