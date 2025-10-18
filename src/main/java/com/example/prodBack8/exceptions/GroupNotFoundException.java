package com.example.prodBack8.exceptions;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(String message) {
        super(message);
    }
}
