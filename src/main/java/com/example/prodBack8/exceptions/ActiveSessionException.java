package com.example.prodBack8.exceptions;

public class ActiveSessionException extends RuntimeException {
    public ActiveSessionException(String message) {
        super(message);
    }
}
