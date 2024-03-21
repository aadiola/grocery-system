package com.aad.grocery.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTransactionDataException extends RuntimeException {

    private int code;

	public InvalidTransactionDataException(String message, int code) {
        super(message);
        this.code = code;
    }
	
	public InvalidTransactionDataException(String message) {
        super(message);
        this.code = 0;
    }


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}

