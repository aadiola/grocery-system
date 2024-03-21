package com.aad.grocery.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionDoesNotExistException extends RuntimeException {

    public TransactionDoesNotExistException(Integer transactionId) {
        super(String.format("Transaction with ID %d does not exist", transactionId));
    }
}

