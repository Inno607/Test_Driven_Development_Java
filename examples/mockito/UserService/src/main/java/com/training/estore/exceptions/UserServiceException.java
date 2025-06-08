package com.training.estore.exceptions;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }
}
