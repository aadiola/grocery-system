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

import com.aad.grocery.exceptions.InvalidProductDataException;
import com.aad.grocery.exceptions.ProductDoesNotExistException;
import com.aad.grocery.model.ProductInventory;
import com.aad.grocery.service.ProductInventoryService;

@RestController
@RequestMapping("/inventory")
@CrossOrigin("*")
public class ProductInventoryController {
	
	static Logger logger = LogManager.getLogger(ProductInventoryController.class.getName());
	
	@Autowired
	ProductInventoryService productInventoryService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllProductInventories(){
		HashMap<String, String> retVal = new HashMap<String, String>();
		try {
    		List<ProductInventory> res = productInventoryService.getProductInventory();
        	return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (Exception e) {
    		logger.error("Error encounted during retrieving of inventory");
    		retVal.put("error", "Error encounted during retrieving of inventory");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} 
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> getProduct(@PathVariable("productId") Integer productId){
		HashMap<String, String> retVal = new HashMap<String, String>();
		try {
			ProductInventory res = productInventoryService.getProductInventory(productId);
			logger.error(res.toString());
        	return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (ProductDoesNotExistException e) {
    		logger.error("Product does not exist.");
    		retVal.put("code", "P401");
    		retVal.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retVal);
    	}catch (Exception e) {
    		logger.error("Error encounted during retrieving of inventory");
    		retVal.put("error", "Error encounted during retrieving of inventory");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} 
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createProductInventory(@RequestBody ProductInventory newProductInventory){
		HashMap<String, String> retVal = new HashMap<String, String>();
		try {
			ProductInventory res = productInventoryService.createProductInventory(newProductInventory);
        	return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (InvalidProductDataException e) {
    		retVal.put("error", e.getMessage());
    		retVal.put("code", "P402");
    		logger.error(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
		} catch (Exception e) {
    		logger.error("Error encounted during creating of inventory");
    		e.printStackTrace();
    		retVal.put("error", "Error encounted during creating of inventory");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} 
	}
	
	@PutMapping("/")
	public ResponseEntity<?> updateProductInventory(@RequestBody ProductInventory newProductInventory){
		HashMap<String, String> retVal = new HashMap<String, String>();
		try {
			ProductInventory res = productInventoryService.updateProductInventory(newProductInventory);
        	return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (ProductDoesNotExistException e) {
    		logger.error("Product does not exist.");
    		retVal.put("code", "P401");
    		retVal.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retVal);
    	} catch (InvalidProductDataException e) {
    		retVal.put("error", e.getMessage());
    		retVal.put("code", "P402");
    		logger.error(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
		}catch (Exception e) {
    		logger.error("Error encounted during updating of inventory");
    		retVal.put("error", "Error encounted during updating of inventory");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} 
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<?> deleteProductInventory(@PathVariable("productId") Integer productId){
		HashMap<String, String> retVal = new HashMap<String, String>();
		try {
			int res = productInventoryService.deleteProductInventory(productId);
        	return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (ProductDoesNotExistException e) {
    		logger.error("Product does not exist.");
    		retVal.put("code", "P401");
    		retVal.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retVal);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Error encounted during deleting of inventory");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} 
	}

}
