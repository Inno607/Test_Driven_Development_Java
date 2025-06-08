package com.training.estore.exceptions;

public class EmailNotificationServiceException extends RuntimeException{
    public EmailNotificationServiceException(String message) {
        super(message);
    }
}
