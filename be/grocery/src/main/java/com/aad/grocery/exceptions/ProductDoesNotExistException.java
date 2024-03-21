package com.aad.grocery.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductDoesNotExistException extends RuntimeException {

    public ProductDoesNotExistException(Integer productId) {
        super(String.format("Product with ID %d does not exist", productId));
    }
}

