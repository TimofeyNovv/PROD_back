package com.example.prodBack8.exceptions;

public class UserInQueueNotFoundException extends RuntimeException {
    public UserInQueueNotFoundException(String message) {
        super(message);
    }
}
