package com.aad.grocery.controllers;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aad.grocery.exceptions.UsernameAlreadyUsedException;
import com.aad.grocery.model.ApplicationUser;
import com.aad.grocery.model.LoginResponseDTO;
import com.aad.grocery.model.RegistrationDTO;
import com.aad.grocery.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
	
	static Logger logger = LogManager.getLogger(AuthenticationController.class.getName());

    @Autowired
    private AuthenticationService authenticationService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO body){
    	HashMap<String, String> retVal = new HashMap<String, String>();
    	try {
    		ApplicationUser res = authenticationService.registerUser(body.getUsername(), body.getPassword());
        	return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (UsernameAlreadyUsedException e) {
    		retVal.put("error", "Username already used.");
    		logger.error("Username already used.");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	} catch (Exception e) {
    		logger.error("Error encounted during registration");
    		retVal.put("error", "Error encounted during registration");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	}
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody RegistrationDTO body){
    	HashMap<String, String> retVal = new HashMap<String, String>();
    	try {
    		LoginResponseDTO res = authenticationService.loginUser(body.getUsername(), body.getPassword());
        	if(res.getUser() == null || res.getJwt().isEmpty()) {
        		HashMap<String, String> err = new HashMap<String, String>();
        		retVal.put("error", "Invalid Login Credentials");
        		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(retVal);
        	}
        	return ResponseEntity.status(HttpStatus.OK).body(res);
    	} catch (Exception e) {
    		logger.error("Error encounted during login");
    		retVal.put("error", "Error encounted during login");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retVal);
    	}
    	
    	
    }
}   

