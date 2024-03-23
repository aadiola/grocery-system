package com.aad.grocery.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aad.grocery.controllers.SalesTransactionController;
import com.aad.grocery.exceptions.InvalidProductDataException;
import com.aad.grocery.exceptions.InvalidTransactionDataException;
import com.aad.grocery.exceptions.ProductDoesNotExistException;
import com.aad.grocery.exceptions.TransactionDoesNotExistException;
import com.aad.grocery.model.ProductInventory;
import com.aad.grocery.model.ProductQuantitySales;
import com.aad.grocery.model.SalesTransaction;
import com.aad.grocery.repositories.ProductInventoryRepository;
import com.aad.grocery.repositories.ProductQuantitySalesRepository;
import com.aad.grocery.repositories.SalesTransactionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
@Transactional
public class SalesTransactionService {
	
	@Autowired
	private SalesTransactionRepository salesTransactionRepository;
	
	@Autowired
	private ProductInventoryRepository productInventoryRepository;
	
	@Autowired
	private ProductQuantitySalesRepository productQuantitySalesRepository;
	
	@Autowired
	private Validator validator;
	
	static Logger logger = LogManager.getLogger(SalesTransactionService.class.getName());
	
	@PersistenceContext
    private EntityManager entityManager;
	
	
	public SalesTransaction createSalesTransaction (SalesTransaction newSalesTransaction) {
		logger.error("create sales called in service");
		
		Set<ConstraintViolation<SalesTransaction>> violations = validator.validate(newSalesTransaction);
		
		for(ConstraintViolation<SalesTransaction> violation : violations) {
			logger.error(violation.getMessage());
			throw new InvalidTransactionDataException(violation.getMessage());
		}
		
		SalesTransaction salesTransaction = new SalesTransaction();
		
		salesTransaction.setDateOfTransaction(newSalesTransaction.getDateOfTransaction());
		salesTransaction.setDiscount(newSalesTransaction.getDiscount());
		salesTransaction.setAmountTendered(newSalesTransaction.getAmountTendered());
		
		if(newSalesTransaction.getProductToQtySold().isEmpty()) {
			throw new InvalidTransactionDataException("Product Sales cannot be empty");
		}
		
		for(ProductQuantitySales prdQtySale : newSalesTransaction.getProductToQtySold()) {
			
			Set<ConstraintViolation<ProductQuantitySales>> productSalesViolations = validator.validate(prdQtySale);
			
			for(ConstraintViolation<ProductQuantitySales> violation : productSalesViolations) {
				logger.error(violation.getMessage());
				throw new InvalidTransactionDataException(violation.getMessage(), 1);
				
			}
			logger.error(String.format("ID: %d", prdQtySale.getProductSalesId()));
			
			Optional<ProductInventory> retVal = Optional.ofNullable(productInventoryRepository.findByProductIdAndArchivedFalse(prdQtySale.getProductId()));

			if(retVal.isEmpty()) {
				throw new ProductDoesNotExistException(prdQtySale.getProductId());
			}
			
			if(retVal.get().getAvailableQuantity() <= prdQtySale.getQuantity()) {
				throw new InvalidTransactionDataException(String.format("Requested quantity for Product %d is greater than available.", prdQtySale.getProductId()), 1);
			}
			
			ProductQuantitySales pr = new ProductQuantitySales();
			pr.setPricePerUnit(prdQtySale.getPricePerUnit());
			pr.setProductId(prdQtySale.getProductId());
			pr.setQuantity(prdQtySale.getQuantity());
			pr.setTransactionId(salesTransaction);

			logger.error("{}", pr.toString());

			salesTransaction.getProductToQtySold().add(pr);
		}
		
		entityManager.persist(salesTransaction);

		return salesTransaction;		
	}
	
	
	
	public List<SalesTransaction> getSalesTransaction(){
		return salesTransactionRepository.findByArchivedFalse();
	}
	
	public SalesTransaction getSalesTransction(Integer transactionId) {
		
		if(transactionId == null) {
			throw new TransactionDoesNotExistException(null);
		}
		
		Optional<SalesTransaction> retVal = Optional.ofNullable(salesTransactionRepository.findByTransactionIdAndArchivedFalse(transactionId));
		if(retVal.isEmpty()) {
			throw new TransactionDoesNotExistException(transactionId);
		}
		
		return retVal.get();
	}
	
	
	public SalesTransaction updateSalesTransaction (SalesTransaction newSalesTransaction) {
		
		if(newSalesTransaction.getTransactionId() == null) {
			throw new TransactionDoesNotExistException(null);
		}
		
		Optional<SalesTransaction> retVal = Optional.ofNullable(salesTransactionRepository.findByTransactionIdAndArchivedFalse(newSalesTransaction.getTransactionId()));
		if(retVal.isEmpty()) {
			throw new TransactionDoesNotExistException(newSalesTransaction.getTransactionId());
		}
		
		Set<ConstraintViolation<SalesTransaction>> violations = validator.validate(newSalesTransaction);
		
		for(ConstraintViolation<SalesTransaction> violation : violations) {
			logger.error(violation.getMessage());
			throw new InvalidTransactionDataException(violation.getMessage());
		}
		
		SalesTransaction salesTransaction = retVal.get();
		
		salesTransaction.setDateOfTransaction(newSalesTransaction.getDateOfTransaction());
		salesTransaction.setDiscount(newSalesTransaction.getDiscount());
		salesTransaction.setAmountTendered(newSalesTransaction.getAmountTendered());
		
		if(newSalesTransaction.getProductToQtySold().isEmpty()) {
			throw new InvalidTransactionDataException("Product Sales cannot be empty");
		}
		
		List<ProductQuantitySales> oldProductQuantitySales = productQuantitySalesRepository.findBySalesTransaction(newSalesTransaction);
		
		
		for(ProductQuantitySales oldSale : oldProductQuantitySales) {
			oldSale.setArchived(true);
			oldSale.setTransactionId(null);
		}
		
		
		salesTransaction.getProductToQtySold().clear();
		for(ProductQuantitySales prdQtySale : newSalesTransaction.getProductToQtySold()) {
			
			// For old products, we exempt them from product validation
			// We use prdtySale id to determine if they are old or not
			
			Set<ConstraintViolation<ProductQuantitySales>> productSalesViolations = validator.validate(prdQtySale);
			
			for(ConstraintViolation<ProductQuantitySales> violation : productSalesViolations) {
				logger.error(violation.getMessage());
				throw new InvalidTransactionDataException(violation.getMessage(), 1);
				
			}
			
			if(prdQtySale.getProductSalesId() == null) {
				Optional<ProductInventory> productVal = Optional.ofNullable(productInventoryRepository.findByProductIdAndArchivedFalse(prdQtySale.getProductId()));

				if(productVal.isEmpty()) {
					throw new ProductDoesNotExistException(prdQtySale.getProductId());
				}
				
				if(productVal.get().getAvailableQuantity() <= prdQtySale.getQuantity()) {
					throw new InvalidTransactionDataException(String.format("Requested quantity for Product %d is greater than available.", prdQtySale.getProductId()), 1);
				}
				
				ProductQuantitySales pr = new ProductQuantitySales();
				pr.setPricePerUnit(prdQtySale.getPricePerUnit());
				pr.setProductId(prdQtySale.getPricePerUnit());
				pr.setQuantity(prdQtySale.getQuantity());
				pr.setTransactionId(salesTransaction);

				salesTransaction.getProductToQtySold().add(pr);
			} else {
				
				Optional<ProductQuantitySales> pr = Optional.ofNullable(productQuantitySalesRepository.findById((prdQtySale.getProductSalesId()))).orElse(null);
				if(pr.isEmpty()) {
					throw new InvalidTransactionDataException("Invalid Product Sales ID");
				}
				
				ProductQuantitySales prUpdated = pr.get();
				prUpdated.setPricePerUnit(prdQtySale.getPricePerUnit());
				prUpdated.setProductId(prdQtySale.getPricePerUnit());
				prUpdated.setQuantity(prdQtySale.getQuantity());
				prUpdated.setTransactionId(salesTransaction);
				prUpdated.setArchived(false);
				
				salesTransaction.getProductToQtySold().add(prUpdated);
			}
			
		}
		
		entityManager.persist(salesTransaction);

		return salesTransaction;		
	}
	
	public int deleteSalesTransaction (Integer transactionId) throws Exception {
		
		if(transactionId == null) {
			throw new TransactionDoesNotExistException(null);
		}
		
		Optional<SalesTransaction> retVal = Optional.ofNullable(salesTransactionRepository.findByTransactionIdAndArchivedFalse(transactionId));
		if(retVal.isEmpty()) {
			throw new TransactionDoesNotExistException(transactionId);
		}

		List<ProductQuantitySales> oldProductQuantitySales = productQuantitySalesRepository.findBySalesTransaction(retVal.get());
		
		
		for(ProductQuantitySales oldSale : oldProductQuantitySales) {
			oldSale.setArchived(true);
			oldSale.setTransactionId(null);
		}
		
		retVal.get().setArchived(true);
		
		entityManager.flush();
		
		return 1;
		
	}
	
	
	
	
	

}
