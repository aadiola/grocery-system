package com.aad.grocery.controllers;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aad.grocery.exceptions.InvalidTransactionDataException;
import com.aad.grocery.exceptions.ProductDoesNotExistException;
import com.aad.grocery.exceptions.TransactionDoesNotExistException;
import com.aad.grocery.model.ProductInventory;
import com.aad.grocery.model.SalesTransaction;
import com.aad.grocery.service.SalesTransactionService;

@RestController
@RequestMapping("/sales")
@CrossOrigin("*")
public class SalesTransactionController {

	static Logger logger = LogManager.getLogger(SalesTransactionController.class.getName());
	
	@Autowired
	SalesTransactionService salesTransactionService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllProductInventories(){
		HashMap<String, String> retVal = new HashMap<String, String>();
		try {
    		List<SalesTransaction> res = salesTransactionService.getSalesTransaction();
        	return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (Exception e) {
    		logger.error("Error encounted during retrieving of sales transactions");
    		retVal.put("error", "Error encounted during retrieving of sales transactions");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} 
	}
	
	@GetMapping("/{transactionId}")
	public ResponseEntity<?> getProduct(@PathVariable("transactionId") Integer transactionId){
		HashMap<String, String> retVal = new HashMap<String, String>();
		try {
			SalesTransaction res = salesTransactionService.getSalesTransction(transactionId);
        	return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (TransactionDoesNotExistException e) {
    		logger.error("Product does not exist.");
    		retVal.put("code", "S401");
    		retVal.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retVal);
    	}catch (Exception e) {
    		logger.error("Error encounted during retrieving of inventory");
    		e.printStackTrace();
    		retVal.put("error", "Error encounted during retrieving of inventory");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} 
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createSalesTransaction(@RequestBody SalesTransaction newSalesTransaction){
		HashMap<String, String> retVal = new HashMap<String, String>();
		logger.error("create sales called");
		try {
			SalesTransaction res = salesTransactionService.createSalesTransaction(newSalesTransaction);
			return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (InvalidTransactionDataException e) {
    		retVal.put("code", "S402");
    		retVal.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} catch (ProductDoesNotExistException e) {
    		logger.error("Transaction does not exist.");
    		retVal.put("code", "S402");
    		retVal.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retVal);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Error encounted during creating of inventory");
    		retVal.put("error", "Error encounted during creating of inventory");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} 
	}
	
	
	@PutMapping("/")
	public ResponseEntity<?> updateSalesTransaction(@RequestBody SalesTransaction newSalesTransaction){
		HashMap<String, String> retVal = new HashMap<String, String>();
		try {
			SalesTransaction res = salesTransactionService.updateSalesTransaction(newSalesTransaction);
			return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (ProductDoesNotExistException e) {
    		retVal.put("code", "S402");
    		retVal.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} catch (TransactionDoesNotExistException e) {
    		logger.error("Transaction does not exist.");
    		retVal.put("code", "S403");
    		retVal.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retVal);
    	} catch (Exception e) {
    		logger.error("Error encounted during creating of inventory");
    		retVal.put("error", "Error encounted during creating of inventory");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} 
	}
	
	@DeleteMapping("/{transactionId}")
	public ResponseEntity<?> deleteSalesTransaction(@PathVariable("transactionId") Integer transactionId){
		HashMap<String, String> retVal = new HashMap<String, String>();
		try {
			int res = salesTransactionService.deleteSalesTransaction(transactionId);
			return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (InvalidTransactionDataException e) {
    		retVal.put("code", "S402");
    		retVal.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} catch (TransactionDoesNotExistException e) {
    		logger.error("Transaction does not exist.");
    		retVal.put("code", "S403");
    		retVal.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retVal);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Error encounted during creating of inventory");
    		retVal.put("error", "Error encounted during creating of inventory");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} 
	}
	
	
	
	
}
