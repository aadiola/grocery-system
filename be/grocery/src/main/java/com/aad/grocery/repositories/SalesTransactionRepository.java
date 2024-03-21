package com.aad.grocery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aad.grocery.model.SalesTransaction;

public interface SalesTransactionRepository extends JpaRepository<SalesTransaction, Integer> {
	SalesTransaction findByTransactionId(Integer transactionId);
	SalesTransaction findByTransactionIdAndArchivedFalse(Integer transactionId);
	List<SalesTransaction> findByArchivedFalse();
}
