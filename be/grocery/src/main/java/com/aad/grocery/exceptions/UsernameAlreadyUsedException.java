package com.aad.grocery.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyUsedException extends RuntimeException {

    public UsernameAlreadyUsedException(String message) {
        super(message);
    }
}
